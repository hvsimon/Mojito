package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity
data class BrowsingHistoryEntity(

    @PrimaryKey
    @ColumnInfo(name = "cocktail_id")
    val cocktailId: String,

    @ColumnInfo(
        name = "updated_at",
        index = true,
    )
    val updatedAt: OffsetDateTime? = OffsetDateTime.now(),
)
