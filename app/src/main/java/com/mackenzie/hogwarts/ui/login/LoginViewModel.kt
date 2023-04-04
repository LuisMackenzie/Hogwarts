package com.mackenzie.hogwarts.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.domain.UserItem
import com.mackenzie.hogwarts.data.toError
import com.mackenzie.hogwarts.ui.detail.DetailViewModel
import com.mackenzie.usecases.AddUsersUseCase
import com.mackenzie.usecases.FindHouseUseCase
import com.mackenzie.usecases.FindUserUseCase
import com.mackenzie.usecases.UserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    addUsersUseCase: AddUsersUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val userLoggedUseCase: UserLoggedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            addUsersUseCase()
        }
    }

    init {
        viewModelScope.launch {
            findUserUseCase(_state.value.user?.name ?: "")
                .catch { cause -> _state.update { it.copy(userError = cause.toError()) }}
                .collect { user -> _state.update { UiState(user = user) } }
        }
    }

    fun onLoginClicked(name: String, pass: String) {
        onFindLoggedUser(name)
        if (verifyUser(name)) {
            if (verifyPass(pass)) {
                _state.value.user?.let { onUserLogged(it) }
                // onUserLogged(_state.value.user!!)
            } else {
                _state.update { it.copy(passError = Error.Unknown("Contrasena incorrecta")) }
            }
        } else {
            _state.update { it.copy(passError = Error.Unknown("Usuario no encontrado")) }
        }
    }

    private fun onFindLoggedUser(name: String) {
        viewModelScope.launch {
            findUserUseCase(name)
                .catch { cause -> _state.update { it.copy(userError = cause.toError()) }}
                .collect { user -> _state.update { UiState(user = user) } }
        }
    }

    fun onUserLogged(user: UserItem) {
        viewModelScope.launch {
            val error = userLoggedUseCase(user)
            // _state.update { it.copy(userError = error) }
        }
    }

    private fun verifyUser(user: String): Boolean = _state.value.user?.name === user


    private fun verifyPass(pass: String): Boolean = _state.value.user?.password === pass

    data class UiState(
        val user: UserItem? = null,
        val userError: Error? = null,
        val passError: Error? = null
    )

}