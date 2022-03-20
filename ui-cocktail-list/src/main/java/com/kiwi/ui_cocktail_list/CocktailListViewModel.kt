package com.kiwi.ui_cocktail_list

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.domain.GetBaseLiquorsUseCase
import com.kiwi.data.domain.GetIBACocktailsUseCase
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.IBACategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBaseLiquorsUseCase: GetBaseLiquorsUseCase,
    getIBACocktailsUseCase: GetIBACocktailsUseCase,
) : ViewModel() {

    private val baseLiquorType: BaseLiquorType? =
        savedStateHandle.get<String>("base_liquor_type")
            ?.let { BaseLiquorType.valueOf(it) }

    private val ibaCategoryType: IBACategoryType? =
        savedStateHandle.get<String>("iba_category_type")
            ?.let { IBACategoryType.valueOf(it) }

    private val titleStringRes: Int
        @StringRes
        get() {
            if (baseLiquorType != null) {
                return when (baseLiquorType) {
                    BaseLiquorType.RUM -> R.string.rum
                    BaseLiquorType.GIN -> R.string.gin
                    BaseLiquorType.VODKA -> R.string.vodka
                    BaseLiquorType.TEQUILA -> R.string.tequila
                    BaseLiquorType.WHISKEY -> R.string.whiskey
                    BaseLiquorType.BRANDY -> R.string.brandy
                }
            }

            if (ibaCategoryType != null) {
                return when (ibaCategoryType) {
                    IBACategoryType.THE_UNFORGETTABLES -> R.string.the_unforgettables
                    IBACategoryType.CONTEMPORARY_CLASSICS -> R.string.contemporary_classics
                    IBACategoryType.NEW_ERA_DRINKS -> R.string.new_era_drinks
                }
            }

            return -1
        }

    private val _uiState = MutableStateFlow(
        CocktailUiState(
            isLoading = true,
            titleStringRes = titleStringRes
        )
    )
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            when {
                baseLiquorType != null -> getBaseLiquorsUseCase(baseLiquorType)
                ibaCategoryType != null -> getIBACocktailsUseCase(ibaCategoryType)
                else -> error("Must pass one of types: BaseLiquorType or IBACategoryType")
            }.onSuccess { data ->
                val items = data.map {
                    CocktailItemUiState(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.thumb,
                    )
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cocktailItems = items,
                    )
                }
            }.onFailure { t ->
                Timber.e(
                    t,
                    "Error while fetching cocktail list by type: " +
                        "$baseLiquorType or $ibaCategoryType"
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = t.localizedMessage,
                    )
                }
            }
        }
    }
}
