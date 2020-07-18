package com.aljon.module.domain.features.auth.models

import com.google.gson.JsonObject

data class Session(
    val id: String? = "",
    val email: String? = "",
    val name: String? = ""
)