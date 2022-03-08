package com.kiwi.ui_collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kiwi.data.repositories.FollowedRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    followedRecipesRepository: FollowedRecipesRepository,
) : ViewModel() {

    val pagedList = followedRecipesRepository.getFollowedPagingData()
        .map { pagingData ->
            pagingData
                .map {
                    UiModel.FollowedModel(
                        followedRecipe = it.followedRecipe,
                        cocktail = it.cocktail,
                    )
                }
                .insertSeparators { before, after ->
                    if (before == null && after == null) {
                        return@insertSeparators null
                    }

                    if (before == null && after != null) {
                        return@insertSeparators UiModel.HeaderModel(
                            after.followedRecipe.catalogName
                        )
                    }

                    if (before != null && after != null) {
                        if (before.followedRecipe.catalogName != after.followedRecipe.catalogName) {
                            return@insertSeparators UiModel.HeaderModel(
                                after.followedRecipe.catalogName
                            )
                        }
                    }

                    null
                }
        }
        .cachedIn(viewModelScope)
}
