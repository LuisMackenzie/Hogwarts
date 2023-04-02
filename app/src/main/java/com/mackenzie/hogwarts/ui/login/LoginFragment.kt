package com.mackenzie.hogwarts.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.FragmentLoginBinding
import com.mackenzie.hogwarts.ui.home.HomeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var homeState: HomeState
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeState = HomeState(requireContext(), findNavController())
        binding = FragmentLoginBinding.bind(view).apply {
            btnLogin.setOnClickListener{
                Toast.makeText(requireContext(), "Not yet Implemented", Toast.LENGTH_SHORT).show()
            }
            btnTest.setOnClickListener{
                homeState.onButtonTestClicked()
            }
        }
    }

}