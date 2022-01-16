package com.kiwi.data.repositories

import androidx.paging.PagingData
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.BaseWine
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Favorite
import com.kiwi.data.entities.RecipeEntity
import dagger.Reusable
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class KiwiRepository @Inject constructor(
    private val cocktailDao: CocktailDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getRecipeByCocktailId(cocktailId: Long): RecipeEntity = withContext(ioDispatcher) {
        cocktailDao.getRecipeBy(cocktailId)
    }

    suspend fun getCocktailBy(cocktailId: String): Cocktail = withContext(ioDispatcher) {
        cocktailDao.getCocktailBy(cocktailId)
    }

    suspend fun getRandomCocktail(): Cocktail = withContext(ioDispatcher) {
        cocktailDao.getRandomCocktail()
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

    fun getFavoritePagingData() = PagingData.from(
        MutableList(cocktailNames.size) { index ->
            Favorite(
                cocktail = cocktails[index],
                catalog = categories.random()
            )
        }.sortedBy { it.catalog }
    )
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
    Cocktail(
        cocktailId = UUID.randomUUID().toString(),
        name = it,
        intro = "",
        gallery = emptyList(),
        steps = emptyList(),
        tips = emptySet(),
    )
}

