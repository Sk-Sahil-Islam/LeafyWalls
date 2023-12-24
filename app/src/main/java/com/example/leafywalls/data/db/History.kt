package com.example.leafywalls.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "history"
)
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)