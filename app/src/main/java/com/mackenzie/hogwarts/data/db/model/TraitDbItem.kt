package com.mackenzie.hogwarts.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TraitDbItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val traitId: String,
    val name: String
)
