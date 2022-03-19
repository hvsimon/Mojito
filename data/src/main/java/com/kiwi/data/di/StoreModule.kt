package com.kiwi.data.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.FullIngredientEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.flow.map

@InstallIn(SingletonComponent::class)
@Module
object StoreModule {

    @Provides
    @Singleton
    fun provideCocktailStore(
        cocktailDao: CocktailDao,
        cocktailApi: CocktailApi,
    ): Store<String, FullDrinkEntity> = StoreBuilder.from(
        fetcher = Fetcher.of { id: String ->
            cocktailApi.lookupFullCocktailDetailsById(id).drinks.first()
        },
        sourceOfTruth = SourceOfTruth.of(
            reader = { id ->
                cocktailDao.getCocktailByIdFlow(id)
            },
            writer = { _, data ->
                cocktailDao.insertCocktails(data)
            }
        )
    ).build()

    @Provides
    @Singleton
    fun provideIngredientStore(
        cocktailDao: CocktailDao,
        cocktailApi: CocktailApi,
    ): Store<String, FullIngredientEntity> =
        StoreBuilder.from<String, FullIngredientEntity, FullIngredientEntity>(
            fetcher = Fetcher.of { name ->
                cocktailApi.searchIngredientByName(name).ingredients.first()
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = { name ->
                    cocktailDao.getIngredientByNameFlow(name)
                },
                writer = { _, data ->
                    cocktailDao.insertIngredients(data)
                }
            )
        ).build()


    @Provides
    @Singleton
    fun provideCategoryStore(
        cocktailDao: CocktailDao,
        cocktailApi: CocktailApi,
    ): Store<Unit, List<CategoryEntity>> =
        StoreBuilder.from<Unit, List<CategoryEntity>, List<CategoryEntity>>(
            fetcher = Fetcher.of {
                cocktailApi.listCategories().drinks
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = {
                    cocktailDao.getAllCocktailCategoryFlow()
                        .map { it.ifEmpty { null } }
                },
                writer = { _, data ->
                    cocktailDao.insertCategories(*data.toTypedArray())
                }
            )
        ).build()
}
