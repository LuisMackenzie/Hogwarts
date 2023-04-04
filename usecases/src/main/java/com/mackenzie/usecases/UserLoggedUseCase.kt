package com.mackenzie.usecases

import com.mackenzie.data.UsersRepository
import com.mackenzie.domain.Error
import com.mackenzie.domain.UserItem
import javax.inject.Inject

class UserLoggedUseCase @Inject constructor(private val repo: UsersRepository) {

    suspend operator fun invoke(user: UserItem): Error? = repo.switchLogged(user)
}