package com.example.leafywalls.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {


    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getHistory(): Flow<List<History>>

    @Upsert
    suspend fun insertHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("DELETE FROM history")
    suspend fun clearHistory()

}