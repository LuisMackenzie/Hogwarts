package com.mackenzie.hogwarts.ui.login

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mackenzie.domain.Error
import com.mackenzie.hogwarts.R

class LoginState(
    private val context: Context,
    private val navController: NavController
) {

    fun onUserLogged() {
        val action = LoginFragmentDirections.actionLoginToHome()
        navController.navigate(action)
    }


    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_api_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }

}

fun Fragment.buildLoginState(
    context: Context = requireContext(),
    navController: NavController = findNavController(),
) = LoginState(context, navController)