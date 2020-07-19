package com.aljon.module.network

import com.aljon.module.network.features.person.models.PersonDTO
import io.reactivex.Single
import retrofit2.http.GET

interface ApiServices {

    @GET("users")
    fun getPersons(): Single<List<PersonDTO>>
}
