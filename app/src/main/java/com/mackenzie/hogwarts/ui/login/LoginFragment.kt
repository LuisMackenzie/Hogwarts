package com.mackenzie.hogwarts.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mackenzie.domain.Error
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.FragmentHomeBinding
import com.mackenzie.hogwarts.databinding.FragmentLoginBinding
import com.mackenzie.hogwarts.ui.common.*
import com.mackenzie.hogwarts.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var loginState: LoginState
    private val  viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        loginState = buildLoginState()
        binding = FragmentLoginBinding.bind(view).apply {
            tietName.requestFocus()
            btnLogin.setOnClickListener{
                if(tietName.text.toString() == "" || tietPass.text.toString() == "") {
                    Toast.makeText(requireContext(), "Usuario o password no pueden estar vacios", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.onLoginClicked(tietName.text.toString(), tietPass.text.toString())
                }
            }
        }
        isUserLogged(sharedPref)

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding withHousesUpdateUI it }

    }

    private fun isUserLogged(sharedPref: SharedPreferences) {
        if (sharedPref.getBoolean(Constants.LOGIN_SUCCESSFUL, false)) {
            loginState.onUserLogged()
        } else {
            Log.e(Constants.TAG_CARD_CONECTION_ERROR, getString(R.string.error_unespecified))
        }
    }

    private infix fun FragmentLoginBinding.withHousesUpdateUI(state: LoginViewModel.UiState) {


        state.user?.let { user ->
            viewModel.onUserLogged(user)
            if(user.isLogged){
                val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean(Constants.LOGIN_SUCCESSFUL, true)
                    apply()
                }

                loginState.onUserLogged()
                Toast.makeText(requireContext(), "Estas Logeado ", Toast.LENGTH_SHORT).show()
            } else {
                // Toast.makeText(requireContext(), getString(R.string.error_unespecified), Toast.LENGTH_SHORT).show()
                Log.e(Constants.TAG_CARD_CONECTION_ERROR, getString(R.string.error_unespecified))
            }
        }

        state.userError?.let {
            Log.e(Constants.TAG_CARD_CONECTION_ERROR, loginState.errorToString(it))
        }

        state.passError?.let {

            when (it) {
                Error.Unknown("Contrasena incorrecta") -> {
                    Toast.makeText(requireContext(), "La pass es incorrecta", Toast.LENGTH_SHORT).show()
                    Log.e(Constants.TAG_CARD_CONECTION_ERROR, loginState.errorToString(it))
                }
                Error.Unknown("Usuario no encontrado") -> {
                    Toast.makeText(requireContext(), "El User es incorrecto", Toast.LENGTH_SHORT).show()
                    Log.e(Constants.TAG_CARD_CONECTION_ERROR, loginState.errorToString(it))
                }
                else -> {
                    Log.e(Constants.TAG_CARD_CONECTION_ERROR, loginState.errorToString(it))
                }
            }

        }

    }

}


