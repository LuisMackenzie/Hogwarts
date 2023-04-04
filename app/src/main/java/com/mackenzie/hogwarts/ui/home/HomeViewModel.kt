package com.mackenzie.hogwarts.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackenzie.domain.Error
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.UserItem
import com.mackenzie.hogwarts.data.toError
import com.mackenzie.usecases.DeleteHouseUseCase
import com.mackenzie.usecases.GetHousesUseCase
import com.mackenzie.usecases.RequestHousesUseCase
import com.mackenzie.usecases.UserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHousesUseCase: GetHousesUseCase,
    private val requestCardsUseCase: RequestHousesUseCase,
    private val userLoggedUseCase: UserLoggedUseCase,
    private val deleteHouseUseCase: DeleteHouseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getHousesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) }}
                .collect{ houses -> _state.update { UiState(houses = houses) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val error = requestCardsUseCase()
            _state.update { it.copy(isLoading = false, error = error) }
        }
    }

    fun onUserLogOut() {
        viewModelScope.launch {
            val error = userLoggedUseCase(UserItem(0, "", "", "", true))
            _state.update { it.copy(error = error) }
        }
    }

    fun onDeleteHouse(house: HouseItem) {
        viewModelScope.launch {
            val error = deleteHouseUseCase(house)
            _state.update { it.copy(error = error) }
        }
    }

    data class UiState(
        val isLoading: Boolean? = null,
        val houses: List<HouseItem>? = null,
        val error: Error? = null
    )
}