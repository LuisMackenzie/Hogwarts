package com.mackenzie.hogwarts.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.ui.favorite.FavoriteFragmentDirections
import com.mackenzie.hogwarts.ui.login.LoginFragmentDirections

class HomeState(
    private val context: Context,
    private val navController: NavController
) {

    fun onHouseClicked(house: HouseItem) {
        val action = HomeFragmentDirections.actionHomeToDetail(house.id)
        navController.navigate(action)
    }

    fun onButttonFavoriteClicked() {
        val action = HomeFragmentDirections.actionHomeToFavorite()
        navController.navigate(action)
    }

    fun onFavoriteClicked(head: HeadItem) {

        val action = FavoriteFragmentDirections.actionFavoriteToDetail(head.id)
        navController.navigate(action)
    }

    fun onButtonTestClicked() {
        val action = LoginFragmentDirections.actionLoginToHome()
        navController.navigate(action)
    }

    fun onLoginClicked() {
        // TODO implement login
        // val action = LoginFragmentDirections.actionLoginToHome()
        // navController.navigate(action)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_api_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}

typealias HouseListener = (HouseItem) -> Unit

fun Fragment.buildHomeState(
    context: Context = requireContext(),
    navController: NavController = findNavController(),
) = HomeState(context, navController)