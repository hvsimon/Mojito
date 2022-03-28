package com.kiwi.data.domain

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.get
import com.kiwi.data.entities.BaseLiquor
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.SimpleDrinkDto
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetBaseLiquorsUseCase @Inject constructor(
    private val baseLiquorsStore: Store<Unit, List<BaseLiquor>>,
    @Named("SearchByIngredient")
    private val searchByIngredientStore: Store<String, List<SimpleDrinkDto>>,
) {

    suspend operator fun invoke(type: BaseLiquorType): Result<List<SimpleDrinkDto>> = runCatching {
        coroutineScope {
            baseLiquorsStore.get(Unit)
                .filter { it.baseLiquor == type }
                .map { async { searchByIngredientStore.get(it.name) } }
                .awaitAll()
                .flatten()
        }
    }
}
