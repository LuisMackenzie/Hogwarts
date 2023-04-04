package com.mackenzie.hogwarts.ui.home

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mackenzie.domain.Error
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.HouseItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.ui.common.Constants
import com.mackenzie.hogwarts.ui.detail.DetailFragmentDirections
import com.mackenzie.hogwarts.ui.favorite.FavoriteFragmentDirections
import com.mackenzie.hogwarts.ui.login.LoginFragmentDirections

class HomeState(
    private val context: Context,
    private val activity: AppCompatActivity,
    private val navController: NavController
) {

    fun onHouseClicked(house: HouseItem) {
        val action = HomeFragmentDirections.actionHomeToDetail(house.id)
        navController.navigate(action)
    }

    fun onButtonFavoriteClicked() {
        val action = HomeFragmentDirections.actionHomeToFavorite()
        navController.navigate(action)
    }

    fun onHomeCloseSesion() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(Constants.LOGIN_SUCCESSFUL, false)
            apply()
        }
        Toast.makeText(context, "Cerrando Sesion", Toast.LENGTH_SHORT).show()
        val action = HomeFragmentDirections.actionHomeToLogin()
        navController.navigate(action)
    }

    fun onDetailCloseSesion() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(Constants.LOGIN_SUCCESSFUL, false)
            apply()
        }
        Toast.makeText(context, "Cerrando Sesion", Toast.LENGTH_SHORT).show()
        val action = DetailFragmentDirections.actionDetailToLogin()
        navController.navigate(action)
    }

    fun onFavoriteCloseSesion() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(Constants.LOGIN_SUCCESSFUL, false)
            apply()
        }
        Toast.makeText(context, "Cerrando Sesion", Toast.LENGTH_SHORT).show()
        val action = FavoriteFragmentDirections.actionFavoriteToLogin()
        navController.navigate(action)
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
    activity: AppCompatActivity = requireActivity() as AppCompatActivity,
    navController: NavController = findNavController(),
) = HomeState(context, activity, navController)