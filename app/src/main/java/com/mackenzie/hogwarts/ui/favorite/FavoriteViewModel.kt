package com.mackenzie.hogwarts.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.Error
import com.mackenzie.domain.UserItem
import com.mackenzie.hogwarts.data.toError
import com.mackenzie.usecases.DeleteHeadUseCase
import com.mackenzie.usecases.GetHeadsUseCase
import com.mackenzie.usecases.UserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getHeadsUseCase: GetHeadsUseCase,
    private val userLoggedUseCase: UserLoggedUseCase,
    private val deleteHeadUseCase: DeleteHeadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getHeadsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) }}
                .collect{ favoriteheads -> _state.update { UiState(heads = favoriteheads) } }
        }
    }

    fun onDeleteFavorite(head: HeadItem) {
        viewModelScope.launch {
            val error = deleteHeadUseCase(head)
            _state.update { _state.value.copy(error = error) }
        }
    }

    fun onUserLogOut() {
        viewModelScope.launch {
            val error = userLoggedUseCase(UserItem(0, "", "", "", true))
            _state.update { it.copy(error = error) }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val heads: List<HeadItem>? = null,
        val error: Error? = null
    )

}