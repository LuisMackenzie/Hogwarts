package com.mackenzie.hogwarts.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mackenzie.domain.HeadItem
import com.mackenzie.domain.TraitItem

@Entity
data class HousesDbItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val animal: String,
    val commonRoom: String,
    val element: String,
    val founder: String,
    val ghost: String,
    val heads: String,
    val houseColours: String,
    val houseId: String,
    val name: String,
    val traits: String,
    val isFavorite: Boolean
)
