package com.mackenzie.hogwarts.ui.favorite

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mackenzie.domain.HeadItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.FragmentFavoriteBinding
import com.mackenzie.hogwarts.ui.common.Constants
import com.mackenzie.hogwarts.ui.common.launchAndCollect
import com.mackenzie.hogwarts.ui.home.HomeState
import com.mackenzie.hogwarts.ui.home.buildHomeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite), OnItemClickListener {

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var homeState: HomeState
    private val favoriteAdapter = FavoriteAdapter(this)
    private lateinit var binding: FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeState = buildHomeState()
        binding = FragmentFavoriteBinding.bind(view).apply {
            recycler.adapter = favoriteAdapter
            detailToolbar.title = getString(R.string.favorite_fragment_title)
            detailToolbar.setNavigationOnClickListener{
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        createMenuToolbar()
        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding updateUI it }
    }

    private infix fun FragmentFavoriteBinding.updateUI(state: FavoriteViewModel.UiState) {

        var count: Int

        state.heads?.let { favoriteHeads ->
            if (favoriteHeads.isEmpty()) {
                ivError.visibility = View.VISIBLE
            }
            ivError.visibility = View.INVISIBLE
            favoriteAdapter.submitList(favoriteHeads.reversed())
        }

        state.error?.let {
            // error = homeState.errorToString(it)
            ivError.visibility = View.VISIBLE
            Toast.makeText(requireContext(), homeState.errorToString(it), Toast.LENGTH_SHORT).show()
            Log.e(Constants.CATEGORY_TAG_FAVORITE, homeState.errorToString(it))
        }

        state.isLoading.let {
            ivLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

    }

    override fun onClick(head: HeadItem) {
        Snackbar.make(requireView(), "You Click on: ${head.firstName}, Long Click for delete", Snackbar.LENGTH_SHORT).show()
    }

    override fun onLongClick(head: HeadItem) {
        showAlert(head)
    }

    fun showAlert(head: HeadItem) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.delete_favorite)
                setPositiveButton(R.string.delete_favorite_confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.onDeleteFavorite(head)
                        Toast.makeText(requireContext(), getString(R.string.delete_favorite_succesful), Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun createMenuToolbar() {
        binding.detailToolbar.inflateMenu(R.menu.menu_main)
        binding.detailToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_close_session -> {
                    homeState.onFavoriteCloseSesion()
                    true
                }
                else -> false
            }
        }
    }

}