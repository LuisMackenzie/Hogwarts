package com.mackenzie.usecases

import com.mackenzie.data.UsersRepository
import com.mackenzie.domain.UserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindUserbyIdUseCase @Inject constructor(private val repo: UsersRepository) {

    operator fun invoke(id: Int): Flow<UserItem> = repo.findById(id)

}