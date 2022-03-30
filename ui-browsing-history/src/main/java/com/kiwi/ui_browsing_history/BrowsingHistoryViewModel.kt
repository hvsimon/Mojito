package com.kiwi.ui_browsing_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kiwi.data.entities.BrowsingHistoryEntity
import com.kiwi.data.repositories.BrowsingHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BrowsingHistoryViewModel @Inject constructor(
    private val browsingHistoryRepository: BrowsingHistoryRepository
) : ViewModel() {

    val pagedList = browsingHistoryRepository.getBrowsingHistoryPagingData()
        .cachedIn(viewModelScope)

    fun clearHistory(entity: BrowsingHistoryEntity) {
        viewModelScope.launch {
            browsingHistoryRepository.delete(entity)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            browsingHistoryRepository.deleteAll()
        }
    }
}
