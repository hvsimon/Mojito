package com.kiwi.data.domain

import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.SimpleDrinkDto
import com.kiwi.data.repositories.CocktailRepository
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetBaseLiquorsUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository,
) {

    suspend operator fun invoke(type: BaseLiquorType): Result<List<SimpleDrinkDto>> = runCatching {
        coroutineScope {
            cocktailRepository.getBaseLiquors()
                .filter { it.baseLiquor == type }
                .map { async { cocktailRepository.searchByIngredient(it.name) } }
                .awaitAll()
                .flatten()
        }
    }
}
