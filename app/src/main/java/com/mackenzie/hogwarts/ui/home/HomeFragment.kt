package com.mackenzie.hogwarts.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.FragmentHomeBinding
import com.mackenzie.hogwarts.ui.common.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeState: HomeState
    private val  viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val housesAdapter = HomeAdapter {  homeState.onHouseClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeState = HomeState(requireContext(), findNavController())

        binding = FragmentHomeBinding.bind(view).apply {
            recycler.adapter = housesAdapter
            listToolbar.title = getString(R.string.home_fragment_title)
            favCardview.setOnClickListener { homeState.onButtonFavoriteClicked() }
        }
        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding withHousesUpdateUI it }

        viewModel.onUiReady()
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

}