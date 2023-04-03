package com.mackenzie.domain

data class UserItem(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val isLogged: Boolean
)
