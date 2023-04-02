package com.mackenzie.hogwarts.data.db

import com.mackenzie.data.datasources.HeadsLocalDataSource
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.hogwarts.data.db.model.HeadDbItem
import com.mackenzie.hogwarts.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HeadsDataSource @Inject constructor(private val dao: HeadDao): HeadsLocalDataSource {

    override val houseHeads: Flow<List<HeadItem>>
        get() = dao.getAll().map { it.toDomainModel() }
    override val headsByName: Flow<List<HeadItem>>
        get() = dao.getAllByName().map { it.toDomainModel() }
    override val headsByLast: Flow<List<HeadItem>>
        get() = dao.getAllByLast().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = dao.headsCount() == 0

    override fun findById(id: Int): Flow<HeadItem> = dao.findById(id).map { it.toDomainModel() }

    override suspend fun save(heads: List<HeadItem>): Error? = tryCall {
        dao.insertAllHeads(heads.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun saveOnly(head: HeadItem): Error? = tryCall {
        dao.insertHead(head.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun delete(head: HeadItem): Error? = tryCall {
        dao.deleteHead(head.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })

    override suspend fun deleteAll(): Error? = tryCall {
        dao.deleteAll()
    }.fold(ifLeft = { it }, ifRight = { null })
}

private fun List<HeadDbItem>.toDomainModel(): List<HeadItem> = map { it.toDomainModel() }

private fun HeadDbItem.toDomainModel(): HeadItem = HeadItem(
    id,
    firstName,
    headId,
    lastName,
    isFavorite
)

private fun List<HeadItem>.fromDomainModel(): List<HeadDbItem> = map { it.fromDomainModel() }

private fun HeadItem.fromDomainModel(): HeadDbItem =
    HeadDbItem(
        id,
        firstName,
        headId,
        lastName,
        isFavorite
    )