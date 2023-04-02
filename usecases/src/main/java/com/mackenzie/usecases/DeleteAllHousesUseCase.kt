package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.Error
import javax.inject.Inject

class DeleteAllHousesUseCase @Inject constructor(private val repo: Repository) {

    suspend operator fun invoke(): Error? = repo.deleteAllHouses()
}