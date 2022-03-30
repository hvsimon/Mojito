package com.kiwi.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kiwi.data.entities.BrowsingHistoryAndCocktail
import com.kiwi.data.entities.BrowsingHistoryEntity

@Dao
interface BrowsingHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(vararg browsingHistoryEntity: BrowsingHistoryEntity)

    @Transaction
    @Query("SELECT * FROM BrowsingHistoryEntity ORDER BY datetime(updated_at) DESC")
    fun getBrowsingHistoryPagingSource(): PagingSource<Int, BrowsingHistoryAndCocktail>

    @Query("DELETE FROM BrowsingHistoryEntity")
    suspend fun deleteAll()
}
