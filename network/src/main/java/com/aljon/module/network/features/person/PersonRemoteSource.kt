package com.aljon.module.network.features.person

import com.aljon.module.domain.features.person.models.Person
import io.reactivex.Single

interface PersonRemoteSource {

    fun getPersons(): Single<List<Person>>
}
