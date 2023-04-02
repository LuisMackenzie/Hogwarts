package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.Error
import com.mackenzie.domain.HouseItem
import javax.inject.Inject

class DeleteHouseUseCase @Inject constructor(private val repo: Repository) {

    suspend operator fun invoke(house: HouseItem): Error? = repo.deleteHouse(house)
}