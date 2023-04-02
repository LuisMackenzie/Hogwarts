package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.HeadItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindHeadUseCase @Inject constructor(private val repo: Repository) {

    operator fun invoke(id: Int): Flow<HeadItem> = repo.findHeadById(id)

}