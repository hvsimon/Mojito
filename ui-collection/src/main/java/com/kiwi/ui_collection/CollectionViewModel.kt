package com.kiwi.ui_collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.kiwi.data.entities.Favorite
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    val pagedList: Flow<PagingData<Favorite>> = flowOf(
        kiwiRepository.getFavoritePagingData()
    )
}
