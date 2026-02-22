package com.example.lexowords.ui.words

import androidx.lifecycle.ViewModel
import com.example.lexowords.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordsScreenViewModel @Inject constructor(
    private val repo: WordRepository
) : ViewModel() {

}
