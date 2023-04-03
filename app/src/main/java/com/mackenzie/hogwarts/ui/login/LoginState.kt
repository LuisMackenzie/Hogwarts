package com.mackenzie.hogwarts.ui.login

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class LoginState(
    private val context: Context,
    private val navController: NavController
) {

    fun onButtonTestClicked() {
        val action = LoginFragmentDirections.actionLoginToHome()
        navController.navigate(action)
    }

    fun onLoginClicked() {
        // TODO implement login
        // val action = LoginFragmentDirections.actionLoginToHome()
        // navController.navigate(action)
    }

}

fun Fragment.buildLoginState(
    context: Context = requireContext(),
    navController: NavController = findNavController(),
) = LoginState(context, navController)