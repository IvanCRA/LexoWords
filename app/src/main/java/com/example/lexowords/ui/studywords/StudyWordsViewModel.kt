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
    private val repository: WordRepository,
) : ViewModel() {
    private val _newWords = mutableStateListOf<Word>()
    private val _currentWord = MutableStateFlow<Word?>(null)
    val currentWord: StateFlow<Word?> = _currentWord

    private val _limitReached = MutableStateFlow(false)
    val limitReached: StateFlow<Boolean> = _limitReached

    private val learningQueue = mutableListOf<Word>()
    private val learnedWords = mutableSetOf<Int>()

    private var totalLearningWords = 0
    var inLearningMode = false
        private set

    private var index = 0

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
        viewModelScope.launch {
            currentWord.value?.let { word ->
                if (inLearningMode) {
                    repository.updateWordStateWithTimestamp(
                        word.id,
                        WordStudyState.TO_REVIEW,
                        System.currentTimeMillis(),
                    )
                    learnedWords.add(word.id)
                    learningQueue.remove(word)
                } else {
                    repository.updateWordState(word.id, WordStudyState.LEARNED)
                }
            }
            moveToNext()
        }
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
                    totalLearningWords = learningQueue.map { it.id }.distinct().size
                    _currentWord.value = learningQueue.firstOrNull()
                } else {
                    moveToNext()
                }
            } else {
                _limitReached.value = true
            }
        }
    }

    fun onStillLearning() {
        viewModelScope.launch {
            currentWord.value?.let { word ->
                repository.updateWordState(word.id, WordStudyState.LEARNING)
                learningQueue.remove(word)
                learningQueue.add(word)
            }
            moveToNext()
        }
    }

    private fun moveToNext() {
        if (inLearningMode) {
            _currentWord.value = learningQueue.firstOrNull()
        } else {
            index++
            _currentWord.value = _newWords.getOrNull(index)
        }
    }

    val progress: StateFlow<Pair<Int, Int>> =
        _currentWord
            .map {
                learnedWords.size to totalLearningWords
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0 to 0)
}
