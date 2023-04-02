package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.Error
import javax.inject.Inject

class RequestHousesUseCase @Inject constructor(private val repo: Repository) {

    suspend operator fun invoke(): Error? = repo.requestHouses()
}