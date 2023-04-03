package com.mackenzie.data

import com.mackenzie.data.datasources.UsersLocalDataSource
import com.mackenzie.domain.Error
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.UserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersDataSource: UsersLocalDataSource
) {

    fun findById(id: Int): Flow<UserItem> = usersDataSource.findById(id)

    fun findByEmail(email: String): Flow<UserItem> = usersDataSource.findByEmail(email)

    /*suspend fun requestUsers(): Error? {
        if(usersDataSource.isEmpty()) {
            val users = listOf(User)

                users.fold(ifLeft = {return it}) {
                    usersDataSource.save(it)
            }
        }
        return null
    }*/

    /*suspend fun requestHouses(): Error? {
        if(localDataSource.isEmpty()) {
            val houses = remoteDataSource.getHouses()
            houses.fold(ifLeft = {return it}) {
                localDataSource.save(it)
            }
        }
        return null
    }*/

    /*suspend fun requestUser(email: String): UserItem? {
        val user = usersDataSource.findByEmail(email)
        if (user != null) return user else return null
    }*/
}

