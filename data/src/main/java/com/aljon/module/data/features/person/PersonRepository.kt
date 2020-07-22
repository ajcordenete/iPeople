package com.aljon.module.data.features.person

import com.aljon.module.domain.features.person.models.Person
import io.reactivex.Single

interface PersonRepository {

    fun getPersons(): Single<List<Person>>
}
