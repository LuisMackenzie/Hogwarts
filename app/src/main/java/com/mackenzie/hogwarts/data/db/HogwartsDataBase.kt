package com.mackenzie.hogwarts.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mackenzie.hogwarts.data.db.model.HeadDbItem
import com.mackenzie.hogwarts.data.db.model.HousesDbItem
import com.mackenzie.hogwarts.data.db.model.TraitDbItem
import com.mackenzie.hogwarts.data.db.model.UserDbItem

@Database(entities = [HousesDbItem::class, UserDbItem::class, HeadDbItem::class, TraitDbItem::class], version = 1, exportSchema = false)
abstract class HogwartsDataBase: RoomDatabase() {
    abstract fun housesDao(): HousesDao
    abstract fun usersDao(): UserDao
    abstract fun headsDao(): HeadDao
}