package com.aljon.module.network.features.person.models

import com.aljon.module.domain.features.person.models.Address
import com.aljon.module.domain.features.person.models.Company
import com.aljon.module.domain.features.person.models.Person

data class PersonDTO(
    val id: String? = "",
    val email: String? = "",
    val name: String? = "",
    val username: String? = "",
    val phone: String? = "",
    val address: Address,
    val company: Company
) {

    companion object {
        fun toDomain(personDTO: PersonDTO): Person {
            return with(personDTO) {
                Person(
                    id = id,
                    email = email,
                    name = name,
                    username = username,
                    phone = phone,
                    address = address,
                    company = company
                )
            }
        }
    }
}
