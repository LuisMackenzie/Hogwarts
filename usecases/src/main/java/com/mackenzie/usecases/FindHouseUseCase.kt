package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.HouseItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindHouseUseCase @Inject constructor(private val repo: Repository) {

    operator fun invoke(id: Int): Flow<HouseItem> = repo.findById(id)

}