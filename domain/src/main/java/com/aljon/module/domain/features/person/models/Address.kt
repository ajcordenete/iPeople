package com.aljon.module.domain.features.person.models

data class Address(
    val street: String? = "",
    val suite: String? = "",
    val city: String? = "",
    val zipcode: String? = "",
    val geo: Geo
)