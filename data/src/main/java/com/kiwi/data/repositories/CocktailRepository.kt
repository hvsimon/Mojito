package com.kiwi.data.repositories

import android.app.Application
import com.kiwi.data.DataJsonParser
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.di.DefaultDispatcher
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.BaseLiquor
import com.kiwi.data.entities.CocktailCategoryPo
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FullIngredientDto
import com.kiwi.data.entities.IBACocktail
import com.kiwi.data.mapper.toCocktailPo
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

    suspend fun randomCocktail(num: Int, loadFromRemote: Boolean): Result<List<CocktailPo>> =
        runCatching {
            if (loadFromRemote) {
                coroutineScope {
                    (1..num)
                        .map {
                            async(ioDispatcher) {
                                cocktailApi.randomCocktail().drinks.first().toCocktailPo()
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

    suspend fun searchCocktailByName(cocktailName: String): List<CocktailPo> =
        withContext(ioDispatcher) {
            cocktailApi.searchCocktailByName(cocktailName).drinks.map { it.toCocktailPo() }
        }

    suspend fun searchCocktailByFirstLetter(firstLetter: Char): List<CocktailPo> =
        withContext(ioDispatcher) {
            cocktailApi.searchCocktailByFirstLetter(firstLetter).drinks.map { it.toCocktailPo() }
                .also { launch { cocktailDao.insertCocktails(*it.toTypedArray()) } }
        }

    suspend fun searchIngredientByName(ingredientName: String): FullIngredientDto =
        withContext(ioDispatcher) {
            cocktailApi.searchIngredientByName(ingredientName).ingredients.first()
        }

    suspend fun searchByIngredient(ingredientName: String): List<CocktailPo> =
        withContext(ioDispatcher) {
            cocktailApi.searchByIngredient(ingredientName).drinks.map { it.toCocktailPo() }
        }

    suspend fun filterByCategory(category: String): List<CocktailPo> =
        withContext(ioDispatcher) {
            cocktailApi.filterByCategory(category).drinks.map { it.toCocktailPo() }
        }

    suspend fun lookupFullCocktailDetailsById(id: String): CocktailPo =
        withContext(ioDispatcher) {
            return@withContext cocktailDao.getCocktailBy(id)
                ?: cocktailApi.lookupFullCocktailDetailsById(id).drinks.first().toCocktailPo()
                    .also { cocktailDao.insertCocktails(it) }
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

    suspend fun listCategories() = withContext(ioDispatcher) {
        cocktailDao.getAllCocktailCategory()
            .ifEmpty {
                cocktailApi.listCategories().drinks.map { CocktailCategoryPo(it.categoryName) }
            }
            .also {
                launch { cocktailDao.insertCocktailCategories(*it.toTypedArray()) }
            }
    }

    suspend fun listIngredients() = withContext(ioDispatcher) {
        cocktailApi.listIngredients().drinks.map { it.ingredientName }
    }
}
