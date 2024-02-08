package com.example.leafywalls.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourite",
    indices = [Index(value = ["photoId"], unique = true)]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val photoId: String,
    val uri: String
)