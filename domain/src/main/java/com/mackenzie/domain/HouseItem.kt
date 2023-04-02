package com.mackenzie.domain

data class HouseItem(
    val id : Int,
    val animal: String,
    val commonRoom: String,
    val element: String,
    val founder: String,
    val ghost: String,
    val heads: List<HeadItem>,
    val houseColours: String,
    val houseId: String,
    val name: String,
    val traits: List<TraitItem>,
    val isFavorite: Boolean
)
