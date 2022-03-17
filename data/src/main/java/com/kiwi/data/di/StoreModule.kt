package com.kiwi.data.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.kiwi.data.api.CocktailApi
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.mapper.toCocktailPo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StoreModule {

    @Provides
    @Singleton
    fun provideCocktailStore(
        cocktailDao: CocktailDao,
        cocktailApi: CocktailApi,
    ): Store<String, CocktailPo> = StoreBuilder.from(
        fetcher = Fetcher.of { id: String ->
            cocktailApi.lookupFullCocktailDetailsById(id).drinks
                .first()
                .toCocktailPo()
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
}
