package com.mackenzie.hogwarts.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mackenzie.hogwarts.data.db.model.DummyData

@Database(entities = [DummyData::class], version = 1, exportSchema = false)
abstract class HogwartsDataBase: RoomDatabase() {
    abstract fun housesDao(): HousesDao
}