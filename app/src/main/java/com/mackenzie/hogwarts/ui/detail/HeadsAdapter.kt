package com.mackenzie.hogwarts.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackenzie.domain.HeadItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.ViewHouseHeadItemBinding
import com.mackenzie.hogwarts.ui.common.*

class HeadsAdapter (private val listener: (HeadItem) -> Unit) : ListAdapter<HeadItem, HeadsAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_house_head_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val head = getItem(position)
        holder.bind(head)
        holder.itemView.setOnClickListener { listener(head) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewHouseHeadItemBinding.bind(view)
        fun bind(head: HeadItem) {
            (head.firstName + " " + head.lastName).also { binding.headTitle.text = it }
            binding.headImage.loadUrlWithCircleCrop(createImageHeadUrl(head.firstName))
            if (head.isFavorite) {
                binding.ivFavs.setImageResource(R.drawable.ic_favorite_on)
            } else {
                binding.ivFavs.setImageResource(R.drawable.ic_favorite_off)
            }
        }
    }

}