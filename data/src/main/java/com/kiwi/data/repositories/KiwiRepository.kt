package com.kiwi.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.BaseWine
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FullDrinkDto
import com.kiwi.data.entities.SimpleDrinkDto
import dagger.Reusable
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class KiwiRepository @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val cocktailApi: CocktailApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    // TODO: random cocktail for each date
    suspend fun randomCocktail(): FullDrinkDto = withContext(ioDispatcher) {
        cocktailApi.randomCocktail().drinks.first()
    }

    suspend fun searchByIngredient(ingredientName: String): List<SimpleDrinkDto> =
        withContext(ioDispatcher) {
            cocktailApi.searchByIngredient(ingredientName).drinks
        }

    suspend fun lookupFullCocktailDetailsById(id: String): FullDrinkDto =
        withContext(ioDispatcher) {
            cocktailApi.lookupFullCocktailDetailsById(id).drinks.first()
        }

    suspend fun getCocktailBy(cocktailId: String): CocktailPo = withContext(ioDispatcher) {
        cocktailDao.getCocktailBy(cocktailId)
    }

    suspend fun getBaseWines(): List<BaseWine> = withContext(ioDispatcher) {
        // TODO: 2022/1/6 load from db
        listOf(
            BaseWine(
                id = "rum",
                imageUrl = "https://images.unsplash.com/photo-1614313511387-1436a4480ebb?ixlib=rb-1.2.1&ixid=Mnw" +
                    "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1760&q=80",
            ),
            BaseWine(
                id = "gin",
                imageUrl = "https://images.unsplash.com/photo-1608885898957-a559228e8749?ixlib=rb-1.2.1&ixid=Mnw" +
                    "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
            ),
            BaseWine(
                id = "vodka",
                imageUrl = "https://images.unsplash.com/photo-1550985543-f47f38aeee65?ixlib=rb-1.2.1&ixid=MnwxMj" +
                    "A3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1335&q=80",
            ),
            BaseWine(
                id = "tequila",
                imageUrl = "https://images.unsplash.com/photo-1516535794938-6063878f08cc?ixlib=rb-1.2.1&ixid=Mnw" +
                    "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=988&q=80",
            ),
            BaseWine(
                id = "whiskey",
                imageUrl = "https://images.unsplash.com/photo-1527281400683-1aae777175f8?ixlib=rb-1.2.1&ixid=Mnw" +
                    "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
            ),
            BaseWine(
                id = "brandy",
                imageUrl = "https://images.unsplash.com/photo-1619451050621-83cb7aada2d7?ixlib=rb-1.2.1&ixid=Mnw" +
                    "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=986&q=80",
            ),
        )
    }

    fun getFavoritePagingData() = Pager(
        config = PagingConfig(pageSize = 50),
    ) {
        cocktailDao.getFavoritePagingData()
    }.flow

//    suspend fun foo() = withContext(ioDispatcher) {
//        cocktailDao.getFavoritePagingData()
//    }
}

private val cocktailNames = listOf(
    "Alexander",
    "Daiquiri",
    "Negroni",
    "Screwdriver",
    "Americano",
    "Derby",
    "Old Fashioned",
)

private val categories =
    listOf("Unforgettable Cocktails", "Contemporary Classic Cocktails", "New Era Cocktails")

private val cocktails = cocktailNames.map {
    CocktailPo(
        cocktailId = UUID.randomUUID().toString(),
        name = it,
        intro = "",
        gallery = emptyList(),
        ingredients = emptyList(),
        steps = emptyList(),
        tips = emptySet(),
    )
}

