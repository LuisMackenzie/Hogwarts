package com.mackenzie.hogwarts.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mackenzie.hogwarts.data.db.model.HousesDbItem
import com.mackenzie.hogwarts.data.db.model.UserDbItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDbItem WHERE id = :id")
    fun findById(id: Int): Flow<UserDbItem>

    @Query("SELECT COUNT(id) FROM UserDbItem")
    suspend fun usersCount(): Int

    @Query("SELECT * FROM UserDbItem WHERE email = :email")
    fun findByEmail(email: String): Flow<UserDbItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(houses: List<UserDbItem>)

}