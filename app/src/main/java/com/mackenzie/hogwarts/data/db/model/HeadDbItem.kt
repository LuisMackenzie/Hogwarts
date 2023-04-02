package com.mackenzie.hogwarts.data.db.model

import androidx.room.PrimaryKey

data class HeadDbItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val firstName: String,
    val headId: String,
    val lastName: String,
    val isFavorite: Boolean
)