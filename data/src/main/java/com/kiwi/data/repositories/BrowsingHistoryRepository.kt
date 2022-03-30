package com.kiwi.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kiwi.data.db.BrowsingHistoryDao
import com.kiwi.data.di.IoDispatcher
import com.kiwi.data.entities.BrowsingHistoryAndCocktail
import com.kiwi.data.entities.BrowsingHistoryEntity
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Reusable
class BrowsingHistoryRepository @Inject constructor(
    private val browsingHistoryDao: BrowsingHistoryDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun record(cocktailId: String) {
        withContext(ioDispatcher) {
            browsingHistoryDao.insertOrUpdate(
                BrowsingHistoryEntity(cocktailId)
            )
        }
    }

    fun getBrowsingHistoryPagingData(): Flow<PagingData<BrowsingHistoryAndCocktail>> = Pager(
        config = PagingConfig(pageSize = 50),
    ) {
        browsingHistoryDao.getBrowsingHistoryPagingSource()
    }.flow

    suspend fun deleteAll() {
        withContext(ioDispatcher) {
            browsingHistoryDao.deleteAll()
        }
    }
}
