package com.mackenzie.data.datasources

import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import kotlinx.coroutines.flow.Flow

interface HeadsLocalDataSource {

    val houseHeads: Flow<List<HeadItem>>
    val headsByName: Flow<List<HeadItem>>
    val headsByLast: Flow<List<HeadItem>>
    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<HeadItem>
    suspend fun save(heads: List<HeadItem>): Error?
    suspend fun saveOnly(head: HeadItem): Error?
    suspend fun delete(head: HeadItem): Error?
    suspend fun deleteAll(): Error?

}