package com.kiwi.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kiwi.data.db.FollowedRecipesDao
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.FollowedRecipe
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Reusable
class FollowedRecipesRepository @Inject constructor(
    private val followedRecipesDao: FollowedRecipesDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun changeRecipeFollowedStatus(cocktailId: String) = withContext(ioDispatcher) {
        if (isRecipeFollowed(cocktailId)) unfollow(cocktailId)
        else follow(cocktailId)
    }

    private suspend fun isRecipeFollowed(cocktailId: String): Boolean {
        return recipeFollowedObservable(cocktailId).first()
    }

    fun recipeFollowedObservable(cocktailId: String): Flow<Boolean> {
        return followedRecipesDao.followedCountByCocktailIdObservable(cocktailId).map { it > 0 }
    }

    private suspend fun follow(cocktailId: String) {
        followedRecipesDao.insertOrUpdateFollowed(
            FollowedRecipe(cocktailId = cocktailId)
        )
    }

    private suspend fun unfollow(cocktailId: String) {
        followedRecipesDao.deleteFollowed(cocktailId)
    }

    fun getFollowedPagingData() = Pager(
        config = PagingConfig(pageSize = 50),
    ) {
        followedRecipesDao.getFollowedPagingData()
    }.flow
}
