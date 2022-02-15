package com.kiwi.data.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.db.KiwiDatabase
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Favorite
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.BufferedReader
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@InstallIn(SingletonComponent::class)
@Module
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        cocktailDaoLazy: Lazy<CocktailDao>,
    ): KiwiDatabase {
        val builder = Room.databaseBuilder(context, KiwiDatabase::class.java, "kiwi.db")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {

                override fun onCreate(db: SupportSQLiteDatabase) {
                    GlobalScope.launch(ioDispatcher) {
                        val cocktailDao = cocktailDaoLazy.get()
                        val cocktails = context.assets
                            .open("cocktails.json")
                            .bufferedReader()
                            .use(BufferedReader::readText)
                            .let { cocktailsJson ->
                                Json.decodeFromString(
                                    ListSerializer(Cocktail.serializer()),
                                    cocktailsJson
                                )
                            }
                        cocktailDao.insertCocktails(*cocktails.toTypedArray())
                        val favorites = cocktails.map {
                            Favorite(
                                catalogName = "My",
                                cocktailId = it.cocktailId
                            )
                        }
                        cocktailDao.insertFavorite(*favorites.toTypedArray())

                    }
                }
            })

        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun provideCocktailDao(db: KiwiDatabase) = db.cocktailDao()
}
