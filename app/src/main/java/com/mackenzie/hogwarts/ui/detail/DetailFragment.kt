package com.mackenzie.hogwarts.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.FragmentDetailBinding
import com.mackenzie.hogwarts.ui.common.Constants
import com.mackenzie.hogwarts.ui.common.createImageUrl
import com.mackenzie.hogwarts.ui.common.launchAndCollect
import com.mackenzie.hogwarts.ui.common.loadUrl
import com.mackenzie.hogwarts.ui.home.HomeState
import com.mackenzie.hogwarts.ui.home.buildHomeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val  viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var homeState: HomeState
    private val headsAdapter = HeadsAdapter {
        viewModel.onFavoriteClicked(it)
        Toast.makeText(requireContext(), "Agregaste a ${it.firstName} a favoritos", Toast.LENGTH_SHORT).show()
    }
    private val traitsAdapter = TraitsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeState = buildHomeState()
        binding = FragmentDetailBinding.bind(view).apply {
            recyclerTraits.adapter = traitsAdapter
            recyclerHeads.adapter = headsAdapter
            detailToolbar.setNavigationOnClickListener{
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding updateUI it }
    }

    private infix fun FragmentDetailBinding.updateUI(state: DetailViewModel.UiState) {

        state.house?.let { savedhouse ->
            headsAdapter.submitList(savedhouse.heads)
            traitsAdapter.submitList(savedhouse.traits)
            ivDetail.loadUrl(createImageUrl(savedhouse.name))
            "Titulo de la casa: ${savedhouse.name}".also { tvDetailTitle.text = it }
            "Animal: ${savedhouse.animal}".also { tvDetailAnimal.text = it }
            "Elemento: ${savedhouse.element}".also { tvDetailElement.text = it }
            "Fundador: ${savedhouse.founder}".also { tvDetailFounder.text = it }
            "Fantasma: ${savedhouse.ghost}".also { tvDetailGhost.text = it }
            "Habitacion: ${savedhouse.commonRoom}".also { tvDetailCommon.text = it }
            "Colores de la casa: ${savedhouse.houseColours}".also { tvDetailColours.text = it }
        }

        state.error?.let {
            homeState.errorToString(it)
            Toast.makeText(requireContext(), homeState.errorToString(it), Toast.LENGTH_SHORT).show()
            Log.e(Constants.TAG_CARD_CONECTION_ERROR, homeState.errorToString(it))
        }
    }

}