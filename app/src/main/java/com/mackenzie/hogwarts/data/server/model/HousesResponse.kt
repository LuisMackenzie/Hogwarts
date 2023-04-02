package com.mackenzie.hogwarts.data.server.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize


data class HousesResponse (
    val houses : List<HousesResult>
    )

@Parcelize
data class HousesResult(
    val animal: String,
    val commonRoom: String,
    val element: String,
    val founder: String,
    val ghost: String,
    val heads: List<HeadResponse>,
    val houseColours: String,
    val id: String,
    val name: String,
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

