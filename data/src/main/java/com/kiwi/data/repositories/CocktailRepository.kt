package com.kiwi.data.repositories

import android.app.Application
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.BaseWineGroup
import com.kiwi.data.entities.Category
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FullIngredientDto
import com.kiwi.data.mapper.toCocktailPo
import dagger.Reusable
import java.nio.charset.Charset
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okio.buffer
import okio.source

@Reusable
class CocktailRepository @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val cocktailApi: CocktailApi,
    private val application: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    // TODO: random cocktail for each date
    suspend fun randomCocktail(): CocktailPo = withContext(ioDispatcher) {
        cocktailApi.randomCocktail().drinks.first().toCocktailPo()
    }

    suspend fun searchCocktailByName(cocktailName: String): List<CocktailPo> =
        withContext(ioDispatcher) {
            cocktailApi.searchCocktailByName(cocktailName).drinks.map { it.toCocktailPo() }
        }

    suspend fun searchIngredientByName(ingredientName: String): FullIngredientDto =
        withContext(ioDispatcher) {
            cocktailApi.searchIngredientByName(ingredientName).ingredients.first()
        }

    suspend fun searchByIngredient(ingredientName: String): List<CocktailPo> =
        withContext(ioDispatcher) {
            cocktailApi.searchByIngredient(ingredientName).drinks.map { it.toCocktailPo() }
        }

    suspend fun lookupFullCocktailDetailsById(id: String): CocktailPo =
        withContext(ioDispatcher) {
            return@withContext cocktailDao.getCocktailBy(id)
                ?: cocktailApi.lookupFullCocktailDetailsById(id).drinks.first().toCocktailPo()
                    .also { cocktailDao.insertCocktails(it) }
        }

    suspend fun getBaseWineGroups(): List<BaseWineGroup> = withContext(ioDispatcher) {
        application.assets
            .open("base_wine.json")
            .source()
            .buffer()
            .use { it.readString(Charset.defaultCharset()) }
            .let { json ->
                Json.decodeFromString(
                    ListSerializer(BaseWineGroup.serializer()),
                    json
                )
            }
    }

    suspend fun getCategories(): List<Category> = withContext(ioDispatcher) {
        listOf(
            Category(
                name = "The Unforgettables",
                imageUrl = "https://images.unsplash.com/photo-1574056067299-a11c5b576e69?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2148&q=80",
            ),
            Category(
                name = "Contemporary Classics",
                imageUrl = "https://images.unsplash.com/photo-1597290282695-edc43d0e7129?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2375&q=80",
            ),
            Category(
                name = "New Era Drinks",
                imageUrl = "https://images.unsplash.com/photo-1553484604-9f524520c793?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2378&q=80",
            ),
        )
    }

    suspend fun listCategories() = withContext(ioDispatcher) {
        cocktailApi.listCategories().drinks.map { it.categoryName }
    }

    suspend fun listIngredients() = withContext(ioDispatcher) {
        cocktailApi.listIngredients().drinks.map { it.ingredientName }
    }
}
