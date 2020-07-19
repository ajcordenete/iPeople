package com.aljon.module.network.features.person

import com.aljon.module.domain.features.person.models.Person
import com.aljon.module.network.ApiServices
import com.aljon.module.network.features.person.models.PersonDTO
import io.reactivex.Single
import javax.inject.Inject

class PersonRemoteSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : PersonRemoteSource {

    override fun getPersons(): Single<List<Person>> {
        return apiServices
            .getPersons()
            .map { persons ->
                persons.map {
                    PersonDTO.toDomain(it)
                }
            }
    }
}
