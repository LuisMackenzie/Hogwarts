package com.mackenzie.data

import com.mackenzie.data.datasources.UsersLocalDataSource
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.UserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersDataSource: UsersLocalDataSource
) {

    fun findById(id: Int): Flow<UserItem> = usersDataSource.findById(id)

    fun findByEmail(email: String): Flow<UserItem> = usersDataSource.findByEmail(email)

    fun findByName(name: String): Flow<UserItem> = usersDataSource.findByName(name)

    suspend fun addDummyUsers(): Error? {
        if(usersDataSource.isEmpty()) {
            val users = createUsers()
            usersDataSource.save(users)
        }
        return null
    }

    suspend fun switchLogged(userItem: UserItem): Error? {
        val updateduser = userItem.copy(isLogged = !userItem.isLogged)
        if (updateduser.isLogged) {
            val error = usersDataSource.saveOnly(updateduser)
            if (error != null) return error
        }
        return null
    }

    private fun createUsers(): List<UserItem> {
        val paco = UserItem(
            id = 0,
            name = "Paco",
            email = "paco@gmail.com",
            password = "1111",
            isLogged = false
        )
        val raquel = UserItem(
            id = 0,
            name = "Raquel",
            email = "raquel@gmail.com",
            password = "2222",
            isLogged = false
        )

        return listOf(paco, raquel)
    }
}

