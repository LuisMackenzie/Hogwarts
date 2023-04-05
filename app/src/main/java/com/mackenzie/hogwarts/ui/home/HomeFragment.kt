package com.mackenzie.hogwarts.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mackenzie.domain.HeadItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.FragmentHomeBinding
import com.mackenzie.hogwarts.ui.common.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeState: HomeState
    private val  viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val housesAdapter = HomeAdapter { homeState.onHouseClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeState = buildHomeState()

        requireActivity().intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        binding = FragmentHomeBinding.bind(view).apply {
            recycler.adapter = housesAdapter
            listToolbar.title = getString(R.string.home_fragment_title)
            favCardview.setOnClickListener { homeState.onButtonFavoriteClicked() }
        }
        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding withHousesUpdateUI it }

        viewModel.onUiReady()

        createMenuToolbar()
    }

    private fun createMenuToolbar() {
        binding.listToolbar.inflateMenu(R.menu.menu_main)
        binding.listToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_close_session -> {
                    showCloseSesionAlert()
                    true
                }
                else -> false
            }
        }
    }

    private infix fun FragmentHomeBinding.withHousesUpdateUI(state: HomeViewModel.UiState) {

        favTitle.text = getString(R.string.favorite_characters)
        favThumb.loadUrl(createImageUrl(getString(R.string.favorite_characters_images)))

        state.houses?.let { savedHouses ->
            housesAdapter.submitList(savedHouses)
        }

        state.error?.let {
            homeState.errorToString(it)
            ivError.visible = true
            ivLoading.visible = false
            Toast.makeText(requireContext(), homeState.errorToString(it), Toast.LENGTH_SHORT).show()
            Log.e(Constants.TAG_CARD_CONECTION_ERROR, homeState.errorToString(it))
        }

        state.isLoading?.let {
            ivLoading.visible = it
        }
    }

    fun showCloseSesionAlert() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.close_sesion)
                setPositiveButton(R.string.close_sesion_confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.onUserLogOut()
                        homeState.onHomeCloseSesion()
                        Toast.makeText(requireContext(), getString(R.string.close_session_successful), Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            }
            builder.create()
        }
        alertDialog?.show()
    }

}