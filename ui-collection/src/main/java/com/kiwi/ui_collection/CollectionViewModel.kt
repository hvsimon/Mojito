package com.kiwi.ui_collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    val pagedList = kiwiRepository.getFavoritePagingData()
        .map { pagingData ->
            pagingData
                .map {
                    UiModel.FavoriteModel(
                        favorite = it.favorite,
                        cocktail = it.cocktail,
                    )
                }
                .insertSeparators { before, after ->
                    if (before == null && after == null) {
                        return@insertSeparators null
                    }

                    if (before == null && after != null) {
                        return@insertSeparators UiModel.HeaderModel(after.favorite.catalogName)
                    }

                    if (before != null && after != null) {
                        if (before.favorite.catalogName != after.favorite.catalogName) {
                            return@insertSeparators UiModel.HeaderModel(after.favorite.catalogName)
                        }
                    }

                    null
                }
        }
        .cachedIn(viewModelScope)
}
