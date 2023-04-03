package com.mackenzie.hogwarts.data.db.datasources

import com.mackenzie.data.datasources.UsersLocalDataSource
import com.mackenzie.domain.Error
import com.mackenzie.domain.UserItem
import com.mackenzie.hogwarts.data.db.dao.UserDao
import com.mackenzie.hogwarts.data.db.model.UserDbItem
import com.mackenzie.hogwarts.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersDataSource @Inject constructor(private val dao: UserDao): UsersLocalDataSource {

    override suspend fun isEmpty(): Boolean = dao.usersCount() == 0

    override fun findById(id: Int): Flow<UserItem> = dao.findById(id).map { it.toDomainModel() }

    override fun findByEmail(email: String): Flow<UserItem> = dao.findByEmail(email).map { it.toDomainModel() }

    override suspend fun save(users: List<UserItem>): Error? = tryCall {
        dao.insertAllUsers(users.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })
}

private fun UserDbItem.toDomainModel(): UserItem = UserItem(
    id,
    name,
    email,
    password,
    isLogged
)

private fun List<UserItem>.fromDomainModel(): List<UserDbItem> = map { it.fromDomainModel() }

private fun UserItem.fromDomainModel(): UserDbItem =
    UserDbItem(
        id,
        name,
        email,
        password,
        isLogged
    )