package com.kiwi.data

import com.dropbox.android.external.store4.get
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.di.StoreModule
import com.kiwi.data.entities.TheCocktailDBResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Test

class StoreTest {

    private val cocktailApi = mockk<CocktailApi>(relaxed = true)
    private val cocktailDao = mockk<CocktailDao>(relaxed = true)

    @Test
    fun `get cocktail by id from remote without local data`() {
        val cocktailStore = StoreModule.provideCocktailStore(
            cocktailDao = cocktailDao,
            cocktailApi = cocktailApi,
            ioDispatcher = Dispatchers.IO,
        )
        val mockCocktailResponse = TheCocktailDBResponse(
            drinks = listOf(TestData.fullDrinkDto1)
        )

        coEvery { cocktailDao.getCocktailById(any()) } returns null andThen TestData.fullDrinkDto1
        coEvery { cocktailApi.lookupFullCocktailDetailsById(any()) } returns mockCocktailResponse

        runBlocking {
            assertThat(cocktailStore.get(""), Is(equalTo(TestData.fullDrinkDto1)))
        }

        coVerify(exactly = 2) { cocktailDao.getCocktailById(any()) }

        coVerifyOrder {
            cocktailDao.getCocktailById(any())
            cocktailApi.lookupFullCocktailDetailsById(any())
            cocktailDao.insertCocktails(any())
            cocktailDao.getCocktailById(any())
        }
    }

    @Test
    fun `get cocktail with local data`() {
        val cocktailStore = StoreModule.provideCocktailStore(
            cocktailDao = cocktailDao,
            cocktailApi = cocktailApi,
            ioDispatcher = Dispatchers.IO,
        )

        coEvery { cocktailDao.getCocktailById(any()) } returns TestData.fullDrinkDto1

        runBlocking {
            assertThat(cocktailStore.get(""), Is(equalTo(TestData.fullDrinkDto1)))
        }

        coVerify(exactly = 1) { cocktailDao.getCocktailById(any()) }
        coVerify(exactly = 0) { cocktailApi.lookupFullCocktailDetailsById(any()) }
        coVerify(exactly = 0) { cocktailDao.insertCocktails(any()) }
    }
}
