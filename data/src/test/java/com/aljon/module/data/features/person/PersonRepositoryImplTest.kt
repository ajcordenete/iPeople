package com.aljon.module.data.features.person

import com.aljon.module.domain.features.person.models.Address
import com.aljon.module.domain.features.person.models.Company
import com.aljon.module.domain.features.person.models.Person
import com.aljon.module.network.features.person.PersonRemoteSource
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class PersonRepositoryImplTest {

    // class under test...
    private lateinit var personRepository: PersonRepository

    private val remoteSource = Mockito.mock(PersonRemoteSource::class.java)

    @Before
    fun setUp() {
        personRepository = PersonRepositoryImpl(remoteSource)
    }

    @Test
    fun `when load persons is successful, should expect complete`() {
        val expected = arrayListOf<Person>()
            .apply {
                add(Person("1", "test1@test.com", "Test 1", "test1", "214-214", Address(), Company()))
                add(Person("2", "test2@test.com", "Test 2", "test2", "214-214", Address(), Company()))
            }.toList()

        `when`(remoteSource.getPersons())
            .thenReturn(Single.just(expected))

        // assert that emitter complete
        personRepository.getPersons()
            .test()
            .assertComplete()
            .dispose()

        verify(remoteSource).getPersons()
    }

    @Test
    fun `when load persons is successful, should return list of Person`() {
        val expected = arrayListOf<Person>()
            .apply {
                add(Person("1", "test1@test.com", "Test 1", "test1", "214-214", Address(), Company()))
                add(Person("2", "test2@test.com", "Test 2", "test2", "214-214", Address(), Company()))
            }.toList()

        `when`(remoteSource.getPersons())
            .thenReturn(Single.just(expected))

        // assert that value of emitter is equal to expected list...
        personRepository.getPersons()
            .test()
            .assertValue(expected)
            .dispose()
    }

    @Test
    fun `when load persons tracks failed, should expect error`() {
        val error = Throwable("Something went wrong")

        `when`(remoteSource.getPersons())
            .thenReturn(Single.error(error))

        // assert that emitter returns error...
        personRepository.getPersons()
            .test()
            .assertError(error)
            .dispose()

        verify(remoteSource).getPersons()
    }
}
