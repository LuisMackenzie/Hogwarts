package com.mackenzie.hogwarts.data.server

import arrow.core.Either
import com.mackenzie.data.datasources.HousesServerDataSource
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.TraitItem
import com.mackenzie.hogwarts.data.server.model.HeadResponse
import com.mackenzie.hogwarts.data.server.model.HousesResult
import com.mackenzie.hogwarts.data.server.model.TraitResponse
import com.mackenzie.hogwarts.data.tryCall
import javax.inject.Inject

class ServerDataSource @Inject constructor(private val remoteService: HogwartsService): HousesServerDataSource {

    override suspend fun getHouses(): Either<Error, List<HouseItem>> = tryCall {
        remoteService
            .getHogwartsHouses()
            .houses
            .toDomainModel()
    }

    override suspend fun getOnlyHouse(houseId: String): HouseItem? {
        val house: HouseItem?
        try {
            house = remoteService.getHouse(houseId).toDomainModel()
        } catch (e: Exception) {
            return null
        }
        return house
    }


}

private fun List<HousesResult>.toDomainModel() : List<HouseItem> = map { it.toDomainModel()}

private fun HousesResult.toDomainModel(): HouseItem = HouseItem(
    id = 0,
    animal,
    commonRoom,
    element,
    founder,
    ghost,
    heads.toDomainHeadModel(),
    houseColours,
    houseId = id,
    name,
    traits.toDomainTraitModel(),
    isFavorite = false
)

private fun List<HeadResponse>.toDomainHeadModel() : List<HeadItem> = map { it.toDomainHeadModel()}

private fun HeadResponse.toDomainHeadModel(): HeadItem = HeadItem(
    id = 0,
    firstName,
    headId = id,
    lastName,
    isFavorite = false
)

private fun List<TraitResponse>.toDomainTraitModel() : List<TraitItem> = map { it.toDomainTraitModel()}

private fun TraitResponse.toDomainTraitModel(): TraitItem = TraitItem(
    id = 0,
    traitId = id,
    name
)