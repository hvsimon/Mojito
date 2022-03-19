package com.kiwi.data.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.entities.CategoryPo
import com.kiwi.data.entities.FullDrinkEntity
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
    fun provideCategoryStore(
        cocktailDao: CocktailDao,
        cocktailApi: CocktailApi,
    ): Store<Unit, List<CategoryPo>> = StoreBuilder.from<Unit, List<CategoryPo>, List<CategoryPo>>(
        fetcher = Fetcher.of {
            cocktailApi.listCategories().drinks
                .map { CategoryPo(it.categoryName) }
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
