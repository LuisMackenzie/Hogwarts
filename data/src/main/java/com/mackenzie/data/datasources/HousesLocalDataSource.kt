package com.mackenzie.data.datasources

import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import kotlinx.coroutines.flow.Flow

interface HousesLocalDataSource {

    val houses: Flow<List<HouseItem>>
    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<HouseItem>
    suspend fun save(houses: List<HouseItem>): Error?
    suspend fun saveOnly(house: HouseItem): Error?
    suspend fun saveHead(head: HeadItem): Error?
    suspend fun delete(house: HouseItem): Error?
    suspend fun deleteAll(): Error?

}