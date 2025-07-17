package com.example.lexowords.ui.studywords

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyWordsViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _newWords = mutableStateListOf<Word>()
    private val _currentWord = MutableStateFlow<Word?>(null)
    val currentWord: StateFlow<Word?> = _currentWord

    private val _limitReached = MutableStateFlow(false)
    val limitReached: StateFlow<Boolean> = _limitReached

    private val learningQueue = mutableStateListOf<Word>()
    private var index = 0
    private var inLearningMode = false

    init {
        loadNewWords()
    }


    private fun loadNewWords() {
        viewModelScope.launch {
            val newWords = repository.getWordsByState(WordStudyState.NEW, limit = 50)
            _newWords.clear()
            _newWords.addAll(newWords)
            index = 0
            _currentWord.value = _newWords.getOrNull(index)
        }
    }

    fun onKnowWord() {
        moveToNext()
    }

    fun onDontKnowWord() {
        viewModelScope.launch {
            if (!inLearningMode && learningQueue.size < 10) {
                currentWord.value?.let {
                    repository.updateWordState(it.id, WordStudyState.LEARNING)
                    learningQueue.add(it)
                }
                if (learningQueue.size == 10) {
                    inLearningMode = true
                    index = 0
                    _currentWord.value = learningQueue.getOrNull(index)
                } else {
                    moveToNext()
                }
            } else if (inLearningMode) {
                moveToNext()
            } else {
                _limitReached.value = true
            }
        }
    }

    private fun moveToNext() {
        index++
        _currentWord.value = if (inLearningMode) {
            learningQueue.getOrNull(index)
        } else {
            _newWords.getOrNull(index)
        }
    }

    val progress: StateFlow<Pair<Int, Int>>
        get() = _currentWord.map {
            val total = learningQueue.size
            val current = if (inLearningMode) index + 1 else 0
            current to total
        }.stateIn(viewModelScope, SharingStarted.Eagerly, 0 to 0)
}
