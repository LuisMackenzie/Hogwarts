package com.mackenzie.data

import com.mackenzie.data.datasources.HousesLocalDataSource
import com.mackenzie.data.datasources.HousesServerDataSource
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: HousesLocalDataSource,
    private val remoteDataSource: HousesServerDataSource
) {

    val savedHouses = localDataSource.houses

    fun findById(id: Int): Flow<HouseItem> = localDataSource.findById(id)

    suspend fun requestHouses(): Error? {
        if(localDataSource.isEmpty()) {
            val houses = remoteDataSource.getHouses()
            houses.fold(ifLeft = {return it}) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun deleteAllHouses(): Error? {
        return localDataSource.deleteAll()
    }

    suspend fun deleteHouse(house: HouseItem): Error? {
        return localDataSource.delete(house)
    }

    suspend fun switchFavorite(headItem: HeadItem): Error? {
        val updatedHead = headItem.copy(isFavorite = !headItem.isFavorite)
        val error: Error?
        if (updatedHead.isFavorite) {
            error = localDataSource.saveHead(updatedHead)
            if (error != null) return error
        }
        return null
    }
}