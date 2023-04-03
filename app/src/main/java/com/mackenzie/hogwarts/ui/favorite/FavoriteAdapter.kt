package com.mackenzie.hogwarts.ui.favorite

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackenzie.domain.HeadItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.ViewMediaItemBinding
import com.mackenzie.hogwarts.ui.common.*

class FavoriteAdapter (private val listener: OnItemClickListener
): ListAdapter<HeadItem, FavoriteAdapter.ViewHolder>(
    basicDiffUtil { old, new -> old.id == new.id }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_media_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val headItem = getItem(position)
        holder.bind(headItem)
        holder.itemView.setOnClickListener { listener.onClick(headItem) }
        holder.itemView.setOnLongClickListener { listener.onLongClick(headItem); true }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ViewMediaItemBinding.bind(view)
        fun bind(head: HeadItem) = with(binding) {
            (head.firstName + " " + head.lastName).also { binding.houseTitle.text = it }
            binding.houseThumb.loadUrl(createImageHeadUrl(head.firstName))
            ivFavs.visibility = if (head.isFavorite) View.VISIBLE else View.GONE

        }
    }
}