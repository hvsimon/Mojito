package com.kiwi.data.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.db.KiwiDatabase
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Ingredient
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                    // TODO: 2022/1/16 insert
                    val cocktailDao = cocktailDaoLazy.get()
                    GlobalScope.launch(ioDispatcher) {
                        val cocktail = Cocktail(
                            cocktailId = UUID.randomUUID().toString(),
                            name = "Mojito",
                            gallery = listOf("https://images.unsplash.com/photo-1609345265499-2133bbeb6ce5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1994&q=80"),
                            intro = "The refreshingly minty-citrus flavours of Cuba's Mojito cocktail perfectly compliment white Rum & is wonderful on a hot summer's day. Highly popular, yet tricky to get right & often served too sweet - the key to a good Mojito is to use plenty of fresh Mint & Lime juice but not too much Sugar.",
                            steps = listOf(
                                "1. Place the Mint, Sugar Syrup & Lime wedges into a highball glass & lightly muddle the ingredients together. The Lime wedges & Mint leaves should be bruised to release their juices & essential oils.",
                                "2. Fill the glass with crushed ice, pour over the White Rum & stir.",
                                "3. Top up with Soda Water & stir well from the bottom up.",
                                "4. Garnish with a sprig of Mint & serve with a straw.",
                            ),
                            tipsAndTricks = setOf(
                                "Given that the Mojito is reliant on fresh Limes & Mint that are available in different sizes & flavour intensity, it is a cocktail that really rewards you if you tune the quantities to accommodate the ingredients you have available & most importantly, your own personal tastes.",
                                "If you make a Mojito & find it too sweet, you could try adding Angostura bitters to cut down on the sweetness.",
                                "Don't have any Limes to hand? Try using Lemons for a twist on the classic Mojito but note that you might need to add a little more Sugar Syrup or use a little less Lemon to balance out the extra sourness.",
                                "If you're out of Sugar syrup, you can use half a teaspoon of fine caster Sugar instead. If using Sugar, you might like to add the White Rum & stir to dissolve the Sugar before adding the crushed Ice.",
                                "Don't have crushed ice? Originally, Cuban bartenders mixed the drink using cubed ice, including mint stalks as well as leaves - try this for an equally tasty yet more rustic feel.",
                            )
                        )
                        val ingredients = listOf(
                            Ingredient(
                                ingredientId = 0,
                                name = "\uD83C\uDF78 白蘭姆酒",
                                amount = "2 shots"
                            ),
                            Ingredient(
                                ingredientId = 1,
                                name = "\uD83E\uDDC2 糖漿",
                                amount = "0.5 shot"
                            ),
                            Ingredient(
                                ingredientId = 2,
                                name = "\uD83C\uDF4B 萊姆片",
                                amount = "4"
                            ),
                            Ingredient(
                                ingredientId = 3,
                                name = "\uD83C\uDF3F 新鮮薄荷",
                                amount = "12 leaves"
                            ),
                            Ingredient(
                                ingredientId = 4,
                                name = "\uD83E\uDD64 蘇打水",
                                amount = "fill to top"
                            ),
                        )

                        cocktailDao.insertRecipe(cocktail, ingredients)
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
