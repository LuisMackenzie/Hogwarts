package com.mackenzie.data.datasources

import arrow.core.Either
import com.mackenzie.domain.Error
import com.mackenzie.domain.HouseItem

interface HousesServerDataSource {

    suspend fun getHouses(): Either<Error, List<HouseItem>>

    suspend fun getOnlyHouse(houseId: String): HouseItem?

}