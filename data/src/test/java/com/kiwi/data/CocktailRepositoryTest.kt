package com.kiwi.data

import android.app.Application
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.entities.CocktailResponse
import com.kiwi.data.repositories.CocktailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test

class CocktailRepositoryTest {

    private val cocktailApi = mockk<CocktailApi>(relaxed = true)
    private val cocktailDao = mockk<CocktailDao>(relaxed = true)
    private val application = mockk<Application>(relaxed = true)

    private lateinit var repository: CocktailRepository

    @Before
    fun setup() {
        repository = CocktailRepository(
            cocktailApi = cocktailApi,
            cocktailDao = cocktailDao,
            application = application,
            ioDispatcher = Dispatchers.IO
        )
    }

    @Test
    fun `get cocktail by id from remote without local data`() {
        val mockCocktailResponse = CocktailResponse(
            drinks = listOf(TestData.fullDrinkDto1)
        )

        coEvery { cocktailDao.getCocktailBy(any()) } returns null
        coEvery { cocktailApi.lookupFullCocktailDetailsById(any()) } returns mockCocktailResponse

        val data = runBlocking {
            repository.lookupFullCocktailDetailsById("")
        }

        coVerifyOrder {
            cocktailDao.getCocktailBy(any())
            cocktailApi.lookupFullCocktailDetailsById(any())
            cocktailDao.insertCocktails(any())
        }

        assertThat(data, Is(equalTo(TestData.cocktailPo1)))
    }

    @Test
    fun `get cocktail by id with local data`() {
        val mockCocktailPo = TestData.cocktailPo1
        coEvery { cocktailDao.getCocktailBy(any()) } returns mockCocktailPo

        val data = runBlocking {
            repository.lookupFullCocktailDetailsById("")
        }

        coVerify(exactly = 1) {
            cocktailDao.getCocktailBy(any())
        }
        coVerify(exactly = 0) {
            cocktailApi.lookupFullCocktailDetailsById(any())
        }
        coVerify(exactly = 0) {
            cocktailDao.insertCocktails(any())
        }

        assertThat(data, Is(equalTo(TestData.cocktailPo1)))
    }
}