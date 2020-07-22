package com.aljon.ipeople.features.person.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aljon.ipeople.core.TestSchedulerProvider
import com.aljon.ipeople.features.person.DisplayablePerson
import com.aljon.module.data.features.auth.AuthRepository
import com.aljon.module.data.features.person.PersonRepository
import com.aljon.module.domain.features.auth.models.Session
import com.aljon.module.domain.features.person.models.Address
import com.aljon.module.domain.features.person.models.Company
import com.aljon.module.domain.features.person.models.Person
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class PersonListViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    // class under test
    private lateinit var viewModel: PersonListViewModel

    private val testScheduler = TestScheduler()
    private val schedulers = TestSchedulerProvider(testScheduler)

    private val personRepository = Mockito.mock(PersonRepository::class.java)
    private val authRepository = Mockito.mock(AuthRepository::class.java)

    private val observer = Mockito.mock(TestObserver::class.java) as TestObserver<PersonListState>

    @Before
    fun setUp() {
        viewModel = PersonListViewModel(personRepository, authRepository)
        viewModel.schedulers = schedulers
        viewModel.state.subscribe(observer)
    }

    @Test
    fun `if load Persons is successful, should return a list of persons`() {
        val expected = arrayListOf<Person>()
            .apply {
                add(Person("1", "test1@test.com", "Test 1", "test1", "214-214", Address(), Company()))
                add(Person("2", "test2@test.com", "Test 2", "test2", "214-214", Address(), Company()))
            }.toList()

        `when`(personRepository.getPersons()).thenReturn(Single.just(expected))

        // maps the expected domain list to displayable list
        val expectedDisplayable = expected.map { DisplayablePerson.fromDomain(it) }

        viewModel.getPersons()
        testScheduler.triggerActions()

        // Then expected should be equal to viewModel list
        assertEquals(expectedDisplayable, viewModel.persons.value)
    }

    @Test
    fun `if load Persons failed, expect state ShowFetchError`() {
        val expected = PersonListState.ShowFetchError

        val error = Throwable("Something went wrong")

        `when`(personRepository.getPersons())
            .thenReturn(Single.error(error))

        viewModel.getPersons()
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(PersonListState::class.java).run {
            // Need 3 invocations for showLoading, hideLoading and error states...
            Mockito.verify(observer, Mockito.times(3)).onNext(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `if load Session is successful, should return the name of current user`() {
        val expected = Session("1", "aljon.cordenete@gmail.com", "Aljon")

        `when`(authRepository.getUserSession()).thenReturn(Single.just(expected))

        viewModel.getSession()
        testScheduler.triggerActions()

        // Then expected should be equal to name from session
        assertEquals(expected.name, viewModel.name.value)
    }

    @Test
    fun `if logout is successful, should expect complete`() {
        `when`(authRepository.logout()).thenReturn(Completable.complete())

        viewModel.logout()
        testScheduler.triggerActions()

        // Then completable should return complete
        authRepository.logout()
            .test()
            .assertComplete()
            .dispose()
    }
}
