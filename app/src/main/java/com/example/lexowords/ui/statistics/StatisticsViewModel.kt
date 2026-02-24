package com.example.lexowords.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class StatsUiState(
    val dailyLimit: Int = 0,
    val learnedToday: Int = 0,
    val repeatedToday: Int = 0,
    val totalLearned: Int = 0,
    val totalRepeated: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalWords: Int = 0,
    val favorites: Int = 0,
    val dueNow: Int = 0,
    val byState: List<Pair<String, Int>> = emptyList(),
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val wordDao: WordDao,
    private val userProfileRepository: UserProfileRepository,
) : ViewModel() {
    private val profileFlow =
        userProfileRepository.profile.map {
            it ?: com.example.lexowords.data.local.entities.UserProfileEntity()
        }

    val uiState: StateFlow<StatsUiState> =
        combine(
            profileFlow,
            wordDao.observeTotalWordsCount(),
            wordDao.observeFavoritesCount(),
            wordDao.observeCountsByState(),
            wordDao.observeDueCount(System.currentTimeMillis()),
        ) { profile, totalWords, favorites, byStateRows, dueNow ->
            StatsUiState(
                dailyLimit = profile.dailyLimit,
                learnedToday = profile.wordsLearnedToday,
                repeatedToday = profile.wordsRepeatedToday,
                totalLearned = profile.totalLearned,
                totalRepeated = profile.totalRepeated,
                currentStreak = profile.currentStreak,
                longestStreak = profile.longestStreak,
                totalWords = totalWords,
                favorites = favorites,
                dueNow = dueNow,
                byState = byStateRows.sortedByDescending { it.count }.map { it.state to it.count },
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), StatsUiState())
}
