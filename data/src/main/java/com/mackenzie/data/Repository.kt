package com.mackenzie.data

import com.mackenzie.data.datasources.HeadsLocalDataSource
import com.mackenzie.data.datasources.HousesLocalDataSource
import com.mackenzie.data.datasources.HousesServerDataSource
import com.mackenzie.data.datasources.UsersLocalDataSource
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: HousesLocalDataSource,
    private val usersDataSource: UsersLocalDataSource,
    private val headsLocalDataSource: HeadsLocalDataSource,
    private val remoteDataSource: HousesServerDataSource
) {

    val savedHouses = localDataSource.houses

    val savedHeads = headsLocalDataSource.houseHeads

    fun findById(id: Int): Flow<HouseItem> = localDataSource.findById(id)

    fun findHeadById(id: Int): Flow<HeadItem> = headsLocalDataSource.findById(id)

    suspend fun requestHouses(): Error? {
        if(localDataSource.isEmpty()) {
            val houses = remoteDataSource.getHouses()
            houses.fold(ifLeft = {return it}) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun requestOnlyHouse(houseId: String): HouseItem? {
        val house = remoteDataSource.getOnlyHouse(houseId)
        if (house != null) return house else return null
    }

    suspend fun deleteAllHouses(): Error? {
        return localDataSource.deleteAll()
    }

    suspend fun deleteAllHeads(): Error? {
        return headsLocalDataSource.deleteAll()
    }

    suspend fun deleteHouse(house: HouseItem): Error? {
        return localDataSource.delete(house)
    }

    suspend fun deleteHead(head: HeadItem): Error? {
        return headsLocalDataSource.delete(head)
    }

    suspend fun switchFavorite(headItem: HeadItem): Error? {
        val updatedHead = headItem.copy(isFavorite = !headItem.isFavorite)
        if (updatedHead.isFavorite) {
            val error = localDataSource.saveHead(updatedHead)
            if (error != null) return error
        }
        return null
    }
}