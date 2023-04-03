package com.mackenzie.data.datasources

import com.mackenzie.domain.Error
import com.mackenzie.domain.UserItem
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {

    suspend fun isEmpty(): Boolean

    fun findById(id: Int): Flow<UserItem>

    fun findByEmail(email: String): Flow<UserItem>

    suspend fun save(users: List<UserItem>): Error?

}