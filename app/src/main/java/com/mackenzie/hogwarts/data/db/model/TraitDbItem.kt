package com.mackenzie.hogwarts.data.db.model

import androidx.room.PrimaryKey

data class TraitDbItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val traitId: String,
    val name: String
)
