package com.aljon.module.domain.features.auth.models

data class User(
    val id: Long? = null,
    val email: String? = "",
    val name: String? = "",
    val password: String? = "",
    val country: String? = ""
)