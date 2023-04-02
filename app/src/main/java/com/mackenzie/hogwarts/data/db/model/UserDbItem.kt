package com.mackenzie.hogwarts.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDbItem (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val isLogged: Boolean
    )