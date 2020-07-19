package com.aljon.ipeople.features.person

import android.os.Parcelable
import com.aljon.module.domain.features.person.models.Person
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplayablePerson(
    val id: String? = "",
    val email: String? = "",
    val name: String? = "",
    val username: String? = "",
    val phone: String? = "",
    val address: String? = "",
    val companyName: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0
) : Parcelable {
    companion object {

        fun fromDomain(person: Person): DisplayablePerson {
            return with(person) {
                DisplayablePerson(
                    id = id,
                    email = email,
                    name = name,
                    username = username,
                    phone = phone,
                    address = address.city,
                    companyName = company.name,
                    latitude = address.geo.latitude,
                    longitude = address.geo.longitude
                )
            }
        }
    }
}
