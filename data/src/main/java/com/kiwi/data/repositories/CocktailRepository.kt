package com.kiwi.data.repositories

import android.app.Application
import com.kiwi.data.DataJsonParser
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.BaseLiquor
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.IBACocktail
import com.kiwi.data.entities.SimpleDrinkDto
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source

@Reusable
class CocktailRepository @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val cocktailApi: CocktailApi,
    private val application: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun randomCocktail(num: Int, loadFromRemote: Boolean): Result<List<FullDrinkEntity>> =
        runCatching {
            if (loadFromRemote) {
                coroutineScope {
                    (1..num)
                        .map {
                            async(ioDispatcher) {
                                cocktailApi.randomCocktail().drinks.first()
                            }
                        }
                        .awaitAll()
                        .also { cocktailDao.insertCocktails(*it.toTypedArray()) }
                }
            } else {
                withContext(ioDispatcher) {
                    cocktailDao.randomCocktail(num)
                }
            }
        }

    suspend fun searchCocktailByName(cocktailName: String): Result<List<FullDrinkEntity>> =
        runCatching {
            withContext(ioDispatcher) {
                cocktailApi.searchCocktailByName(cocktailName).drinks
            }
        }

    suspend fun searchCocktailByFirstLetter(firstLetter: Char): List<FullDrinkEntity> =
        withContext(ioDispatcher) {
            cocktailApi.searchCocktailByFirstLetter(firstLetter).drinks
                .also { launch { cocktailDao.insertCocktails(*it.toTypedArray()) } }
        }

    suspend fun searchByIngredient(ingredientName: String): List<SimpleDrinkDto> =
        withContext(ioDispatcher) {
            cocktailApi.searchByIngredient(ingredientName).drinks
        }

    suspend fun filterByCategory(category: String): Result<List<SimpleDrinkDto>> =
        runCatching {
            withContext(ioDispatcher) {
                cocktailApi.filterByCategory(category).drinks
            }
        }

    suspend fun getBaseLiquors(): List<BaseLiquor> = withContext(ioDispatcher) {
        // TODO: Implement cache mechanism
        application.assets
            // TODO: Do not hardcode file path
            .open("base_liquors.json")
            .source()
            .buffer()
            .use { DataJsonParser.parseBaseLiquorData(it) }
    }

    suspend fun getIBACocktails(): List<IBACocktail> = withContext(ioDispatcher) {
        // TODO: Implement cache mechanism
        application.assets
            // TODO: Do not hardcode file path
            .open("iba_cocktails.json")
            .source()
            .buffer()
            .use { DataJsonParser.parseIBACocktailData(it) }
    }

    suspend fun listIngredients() = withContext(ioDispatcher) {
        cocktailApi.listIngredients().drinks.map { it.ingredientName }
    }
}
