package com.kiwi.ui_cocktail_list

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.get
import com.kiwi.data.domain.GetBaseLiquorsUseCase
import com.kiwi.data.domain.GetIBACocktailsUseCase
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.IBACategoryType
import com.kiwi.data.entities.SimpleDrinkDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
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
    @Named("FilterByCategory")
    private val filterByCategoryStore: Store<String, List<SimpleDrinkDto>>,
) : ViewModel() {

    private val type = savedStateHandle.get<CocktailListType>("type")!!
    val keyword = savedStateHandle.get<String>("keyword")!!

    private val titleStringRes: Int?
        @StringRes
        get() {
            return when (type) {
                CocktailListType.BASE_LIQUOR -> when (BaseLiquorType.valueOf(keyword)) {
                    BaseLiquorType.RUM -> R.string.rum
                    BaseLiquorType.GIN -> R.string.gin
                    BaseLiquorType.VODKA -> R.string.vodka
                    BaseLiquorType.TEQUILA -> R.string.tequila
                    BaseLiquorType.WHISKEY -> R.string.whiskey
                    BaseLiquorType.BRANDY -> R.string.brandy
                }
                CocktailListType.IBA_CATEGORY -> when (IBACategoryType.valueOf(keyword)) {
                    IBACategoryType.THE_UNFORGETTABLES -> R.string.the_unforgettables
                    IBACategoryType.CONTEMPORARY_CLASSICS -> R.string.contemporary_classics
                    IBACategoryType.NEW_ERA_DRINKS -> R.string.new_era_drinks
                }
                CocktailListType.CATEGORY -> null
            }
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
            when (type) {
                CocktailListType.BASE_LIQUOR ->
                    getBaseLiquorsUseCase(BaseLiquorType.valueOf(keyword))
                CocktailListType.IBA_CATEGORY ->
                    getIBACocktailsUseCase(IBACategoryType.valueOf(keyword))
                CocktailListType.CATEGORY -> runCatching { filterByCategoryStore.get(keyword) }
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
                Timber.e(t, "Error while fetching cocktail list by type: $type, keyword: $keyword")
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
