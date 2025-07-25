package com.example.lexowords.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexowords.data.local.db.DatabaseInitializer
import com.example.lexowords.domain.usecase.PrepareWordsForReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val databaseInitializer: DatabaseInitializer,
    private val prepareWordsForReviewUseCase: PrepareWordsForReviewUseCase
) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        viewModelScope.launch {
            databaseInitializer.initializeIfNeeded()
            prepareWordsForReviewUseCase()
            _isReady.value = true
        }
    }
}
