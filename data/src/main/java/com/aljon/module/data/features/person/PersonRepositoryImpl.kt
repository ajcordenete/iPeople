package com.aljon.module.data.features.person

import com.aljon.module.domain.features.person.models.Person
import com.aljon.module.network.features.person.PersonRemoteSource
import io.reactivex.Single
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val remote: PersonRemoteSource
) : PersonRepository {

    override fun getPersons(): Single<List<Person>> {
        return remote.getPersons()
    }
}
