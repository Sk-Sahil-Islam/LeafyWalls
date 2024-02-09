package com.example.leafywalls.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {


    @Query("SELECT * FROM favourite ORDER BY id DESC")
    fun getFavourite(): Flow<List<Favorite>>

    @Upsert
    suspend fun insertFavourite(favorite: Favorite)

    @Delete
    suspend fun deleteFavourite(favorite: Favorite)

    @Delete
    suspend fun deleteMultipleFavorite(favorites: List<Favorite>)

}