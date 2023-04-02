package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.HouseItem
import javax.inject.Inject

class RequestOnlyHouseUseCase @Inject constructor(private val repo: Repository){

    suspend operator fun invoke(houseId: String) = repo.requestOnlyHouse(houseId)

}