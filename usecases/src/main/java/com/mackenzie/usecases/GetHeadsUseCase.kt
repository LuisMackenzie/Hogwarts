package com.mackenzie.usecases

import com.mackenzie.data.Repository
import javax.inject.Inject

class GetHeadsUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke() = repo.savedHeads
}