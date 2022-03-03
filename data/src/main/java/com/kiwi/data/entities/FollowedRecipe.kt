package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(
    indices = [
        Index(value = ["cocktail_id"], unique = true)
    ]
)
data class FollowedRecipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "catalog_name") val catalogName: String = "DEFAULT",
    @ColumnInfo(name = "cocktail_id") val cocktailId: String,
    @ColumnInfo(name = "followed_at") val followedAt: OffsetDateTime? = OffsetDateTime.now(),
)
