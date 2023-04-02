package com.mackenzie.hogwarts.data.db

import com.google.gson.Gson
import com.mackenzie.data.datasources.HousesLocalDataSource
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.TraitItem
import com.mackenzie.hogwarts.data.db.model.HeadDbItem
import com.mackenzie.hogwarts.data.db.model.HousesDbItem
import com.mackenzie.hogwarts.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HousesDataSource @Inject constructor(private val dao: HousesDao, private val headDao: HeadDao) : HousesLocalDataSource {

    override val houses: Flow<List<HouseItem>> = dao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = dao.cardsCount() == 0

    override fun findById(id: Int): Flow<HouseItem> = dao.findById(id).map { it.toDomainModel() }

    override suspend fun save(houses: List<HouseItem>): Error? = tryCall {
        dao.insertAllHouses(houses.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun saveOnly(house: HouseItem): Error? = tryCall {
        dao.updateHouse(house.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun saveHead(head: HeadItem): Error? = tryCall {
        headDao.insertHead(head.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun delete(house: HouseItem): Error? = tryCall {
        dao.deleteHouse(house.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun deleteAll(): Error? = tryCall {
        dao.deleteAll()
    }.fold(ifLeft = { it }, ifRight = { null })
}

private fun List<HousesDbItem>.toDomainModel(): List<HouseItem> = map { it.toDomainModel() }

private fun HousesDbItem.toDomainModel(): HouseItem = HouseItem(
    id,
    animal,
    commonRoom,
    element,
    founder,
    ghost,
    fromHeadDatabase(heads),
    houseColours,
    houseId,
    name,
    fromTraitDatabase(traits),
    isFavorite
)

private fun List<HouseItem>.fromDomainModel(): List<HousesDbItem> = map { it.fromDomainModel() }

private fun HouseItem.fromDomainModel(): HousesDbItem =
    HousesDbItem(
        id,
        animal,
        commonRoom,
        element,
        founder,
        ghost,
        listHeadToDatabase(heads),
        houseColours,
        houseId,
        name,
        listTraitToDatabase(traits),
        isFavorite
    )


private fun HeadItem.fromDomainModel(): HeadDbItem =
    HeadDbItem(
        id,
        firstName,
        headId,
        lastName,
        isFavorite
    )

fun listHeadToDatabase(variants: List<HeadItem>):String =
    Gson().toJson(variants)

fun fromHeadDatabase(chain: String) :List<HeadItem> =
    Gson().fromJson(chain, Array<HeadItem>::class.java).asList()

fun listTraitToDatabase(variants: List<TraitItem>):String =
    Gson().toJson(variants)

fun fromTraitDatabase(chain: String) :List<TraitItem> =
    Gson().fromJson(chain, Array<TraitItem>::class.java).asList()