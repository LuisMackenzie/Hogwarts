package com.mackenzie.usecases

import com.mackenzie.data.UsersRepository
import com.mackenzie.domain.Error
import javax.inject.Inject

class AddUsersUseCase @Inject constructor(private val repo: UsersRepository) {

    suspend operator fun invoke(): Error? = repo.addDummyUsers()
}