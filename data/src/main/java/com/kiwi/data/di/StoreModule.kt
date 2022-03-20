package com.kiwi.data.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CategoryDao
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.db.IngredientDao
import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.FullIngredientEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@InstallIn(SingletonComponent::class)
@Module
object StoreModule {

    @Provides
    @Singleton
    fun provideCocktailStore(
        cocktailDao: CocktailDao,
        cocktailApi: CocktailApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): Store<String, FullDrinkEntity> = StoreBuilder.from(
        fetcher = Fetcher.of { id: String ->
            cocktailApi.lookupFullCocktailDetailsById(id).drinks.first()
        },
        sourceOfTruth = SourceOfTruth.of(
            nonFlowReader = { id ->
                withContext(ioDispatcher) { cocktailDao.getCocktailById(id) }
            },
            writer = { _, data ->
                withContext(ioDispatcher) { cocktailDao.insertCocktails(data) }
            }
        )
    ).build()

    @Provides
    @Singleton
    fun provideIngredientStore(
        ingredientDao: IngredientDao,
        cocktailApi: CocktailApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): Store<String, FullIngredientEntity> =
        StoreBuilder.from<String, FullIngredientEntity, FullIngredientEntity>(
            fetcher = Fetcher.of { name ->
                cocktailApi.searchIngredientByName(name).ingredients.first()
            },
            sourceOfTruth = SourceOfTruth.of(
                nonFlowReader = { name ->
                    withContext(ioDispatcher) { ingredientDao.getIngredientByName(name) }
                },
                writer = { _, data ->
                    withContext(ioDispatcher) { ingredientDao.insertIngredients(data) }
                }
            )
        ).build()

    @Provides
    @Singleton
    fun provideCategoryStore(
        categoryDao: CategoryDao,
        cocktailApi: CocktailApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): Store<Unit, List<CategoryEntity>> =
        StoreBuilder.from<Unit, List<CategoryEntity>, List<CategoryEntity>>(
            fetcher = Fetcher.of {
                cocktailApi.listCategories().drinks
            },
            sourceOfTruth = SourceOfTruth.of(
                nonFlowReader = {
                    withContext(ioDispatcher) {
                        categoryDao.getAllCocktailCategory().ifEmpty { null }
                    }
                },
                writer = { _, data ->
                    withContext(ioDispatcher) {
                        categoryDao.insertCategories(*data.toTypedArray())
                    }
                }
            )
        ).build()
}
