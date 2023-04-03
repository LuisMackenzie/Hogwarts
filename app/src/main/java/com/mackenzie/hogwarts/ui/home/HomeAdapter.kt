package com.mackenzie.hogwarts.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackenzie.domain.HouseItem
import com.mackenzie.hogwarts.R
import com.mackenzie.hogwarts.databinding.ViewMediaItemBinding
import com.mackenzie.hogwarts.ui.common.*

class HomeAdapter(private val listener: HouseListener
): ListAdapter<HouseItem, HomeAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_media_item, false)
        return ViewHolder(view)
    }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val houseItem = getItem(position)
            holder.bind(houseItem)
            holder.itemView.setOnClickListener { listener(houseItem) }
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

            private val binding = ViewMediaItemBinding.bind(view)

            fun bind(house: HouseItem) = with(binding) {
                houseTitle.text = house.name
                houseThumb.loadUrl(createImageUrl(house.name))
                ivFavs.visibility = if (house.isFavorite) View.VISIBLE else View.GONE

            }
        }
}