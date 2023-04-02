package com.mackenzie.hogwarts.data.db

import androidx.room.*
import com.mackenzie.hogwarts.data.db.model.HeadDbItem
import com.mackenzie.hogwarts.data.db.model.HousesDbItem
import kotlinx.coroutines.flow.Flow

interface HeadDao {

    @Query("SELECT * FROM HeadDbItem")
    fun getAll(): Flow<List<HeadDbItem>>

    @Query("SELECT * FROM HeadDbItem ORDER BY firstName ASC")
    fun getAllByName(): Flow<List<HeadDbItem>>

    @Query("SELECT * FROM HeadDbItem ORDER BY lastName ASC")
    fun getAllByLast(): Flow<List<HeadDbItem>>

    @Query("SELECT * FROM HeadDbItem WHERE id = :id")
    fun findById(id: Int): Flow<HeadDbItem>

    @Query("SELECT COUNT(id) FROM HeadDbItem")
    suspend fun headsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHead(head: HeadDbItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHeads(heads: List<HeadDbItem>)

    @Update
    suspend fun updateHead(head: HeadDbItem)

    @Delete
    suspend fun deleteHead(head: HeadDbItem)

    @Query("DELETE FROM HeadDbItem")
    suspend fun deleteAll()

}