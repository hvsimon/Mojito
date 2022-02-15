package com.kiwi.ui_collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    val pagedList = kiwiRepository.getFavoritePagingData()
        .cachedIn(viewModelScope)}
