package com.aljon.module.domain.features.person.models

data class Person(
    val id: String? = "",
    val email: String? = "",
    val name: String? = "",
    val username: String? = "",
    val phone: String? = "",
    val address: Address,
    val company: Company
)