package com.mackenzie.hogwarts.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.hogwarts.data.toError
import com.mackenzie.hogwarts.di.HouseId
import com.mackenzie.usecases.FindHouseUseCase
import com.mackenzie.usecases.SwitchHeadFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @HouseId private val cardId: Int,
    findHouseUseCase: FindHouseUseCase,
    private val switchHeadFavoriteUseCase: SwitchHeadFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findHouseUseCase(cardId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) }}
                .collect { house -> _state.update { UiState(house = house) } }
        }
    }

    fun onFavoriteClicked(head: HeadItem) {
        viewModelScope.launch {
            val error = switchHeadFavoriteUseCase(head)
            _state.update { it.copy(error = error) }
        }
    }

    data class UiState(
        val house: HouseItem? = null,
        val error: Error? = null
    )

}