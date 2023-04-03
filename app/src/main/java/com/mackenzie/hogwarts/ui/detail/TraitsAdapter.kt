package com.mackenzie.hogwarts.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackenzie.domain.TraitItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.ViewHouseHeadItemBinding
import com.mackenzie.hogwarts.ui.common.basicDiffUtil
import com.mackenzie.hogwarts.ui.common.inflate

class TraitsAdapter : ListAdapter<TraitItem, TraitsAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id })  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_house_head_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trait = getItem(position)
        holder.bind(trait)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewHouseHeadItemBinding.bind(view)
        fun bind(trait: TraitItem) {
            binding.headTitle.text = trait.name
            binding.ivFavs.visibility = View.INVISIBLE
        }
    }
}