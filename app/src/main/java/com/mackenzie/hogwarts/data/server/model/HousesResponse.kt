package com.mackenzie.hogwarts.data.server.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

data class HousesResponse (
    val houses: List<HousesResult>
    )

@Parcelize
data class HousesResult(
    val id: String,
    val name: String,
    val houseColours: String,
    val founder: String,
    val animal: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    val heads: List<HeadResponse>,
    val traits: List<TraitResponse>
) : Parcelable

@Parcelize
data class HeadResponse(
    val firstName: String,
    val id: String,
    val lastName: String
) : Parcelable

@Parcelize
data class TraitResponse(
    val id: String,
    val name: String
) : Parcelable


