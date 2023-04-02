package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import javax.inject.Inject

class DeleteHeadUseCase @Inject constructor(private val repo: Repository) {

    suspend operator fun invoke(head: HeadItem): Error? = repo.deleteHead(head)
}