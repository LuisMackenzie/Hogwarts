package com.mackenzie.hogwarts.data.db

import androidx.room.*
import com.mackenzie.hogwarts.data.db.model.HousesDbItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HousesDao {

    @Query("SELECT * FROM HousesDbItem")
    fun getAll(): Flow<List<HousesDbItem>>

    @Query("SELECT * FROM HousesDbItem WHERE id = :id")
    fun findById(id: Int): Flow<HousesDbItem>

    @Query("SELECT COUNT(id) FROM HousesDbItem")
    suspend fun cardsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouse(house: HousesDbItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHouses(houses: List<HousesDbItem>)

    @Update
    suspend fun updateHouse(house: HousesDbItem)

    @Delete
    suspend fun deleteHouse(house: HousesDbItem)

    @Query("DELETE FROM HousesDbItem")
    suspend fun deleteAll()

}