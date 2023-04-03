package com.mackenzie.usecases

import com.mackenzie.data.Repository
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.Error
import javax.inject.Inject

class SwitchHeadFavoriteUseCase @Inject constructor(private val repo: Repository) {

    suspend operator fun invoke(head: HeadItem): Error? = repo.switchFavorite(head)
}