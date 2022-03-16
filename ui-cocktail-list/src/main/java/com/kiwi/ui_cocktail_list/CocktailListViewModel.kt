package com.kiwi.ui_cocktail_list

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.IBACategoryType
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
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

    private val _uiState = MutableStateFlow(CocktailUiState(titleStringRes = titleStringRes))
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val list = when {
                baseLiquorType != null -> getCocktailsByBaseLiquors(baseLiquorType)
                ibaCategoryType != null -> getIBACocktails(ibaCategoryType)
                else -> error("Must pass one of types: BaseLiquorType or IBACategoryType")
            }

            _uiState.update {
                it.copy(cocktailItems = list)
            }
        }
    }

    private suspend fun getCocktailsByBaseLiquors(type: BaseLiquorType) =
        cocktailRepository.getBaseLiquors()
            .filter { it.baseLiquor == type }
            .map { viewModelScope.async { cocktailRepository.searchByIngredient(it.name) } }
            .awaitAll()
            .flatten()
            .map {
                CocktailItemUiState(
                    id = it.cocktailId,
                    name = it.name,
                    imageUrl = it.gallery.firstOrNull() ?: "",
                )
            }

    private suspend fun getIBACocktails(type: IBACategoryType) =
        cocktailRepository.getIBACocktails()
            .filter { it.iba == type && it.id.isNotEmpty() }
            .map {
                viewModelScope.async { cocktailRepository.lookupFullCocktailDetailsById(it.id) }
            }
            .awaitAll()
            .map {
                CocktailItemUiState(
                    id = it.cocktailId,
                    name = it.name,
                    imageUrl = it.gallery.firstOrNull() ?: "",
                )
            }
}
