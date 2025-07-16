package com.example.lexowords.ui.studywords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.local.entities.WordEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StudyWordsViewModel @Inject constructor(
    private val wordDao: WordDao,
) : ViewModel() {
    val words: Flow<PagingData<WordEntity>> =
        Pager(PagingConfig(pageSize = 30)) {
            wordDao.getAllWordsPaging()
        }.flow.cachedIn(viewModelScope)
}
