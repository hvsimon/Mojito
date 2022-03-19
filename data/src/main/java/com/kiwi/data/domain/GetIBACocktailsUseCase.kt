package com.kiwi.data.domain

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.get
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.IBACategoryType
import com.kiwi.data.repositories.CocktailRepository
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetIBACocktailsUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val cocktailStore: Store<String, CocktailPo>,
) {

    suspend operator fun invoke(type: IBACategoryType): Result<List<CocktailPo>> = runCatching {
        coroutineScope {
            cocktailRepository.getIBACocktails()
                .filter { it.iba == type && it.id.isNotEmpty() }
                .map { async { cocktailStore.get(it.id) } }
                .awaitAll()
        }
    }
}
