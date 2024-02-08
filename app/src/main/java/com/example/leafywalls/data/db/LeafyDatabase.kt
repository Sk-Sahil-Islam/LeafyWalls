package com.example.leafywalls.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Favorite::class],
    version = 1
)
abstract class LeafyDatabase : RoomDatabase() {

    abstract val favouriteDao: FavouriteDao
}