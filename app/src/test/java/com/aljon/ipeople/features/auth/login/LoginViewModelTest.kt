package com.aljon.ipeople.features.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aljon.ipeople.core.TestSchedulerProvider
import com.aljon.module.data.features.auth.AuthRepository
import com.aljon.module.domain.features.auth.models.Session
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*

class LoginViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    // class under test
    private lateinit var viewModel: LoginViewModel

    private val testScheduler = TestScheduler()
    private val schedulers = TestSchedulerProvider(testScheduler)

    private val authRepository = Mockito.mock(AuthRepository::class.java)

    private val observer = Mockito.mock(TestObserver::class.java) as TestObserver<LoginState>

    @Before
    fun setUp() {
        viewModel = LoginViewModel(authRepository)
        viewModel.schedulers = schedulers
        viewModel.state.subscribe(observer)
    }

    @Test
    fun `if login is successful, should expect LoginSuccess state`() {
        val expected = LoginState.LoginSuccess

        val inputEmail = "aljon.cordenete@gmail.com"
        val inputPassword = "password"

        val session = Session("1", "aljon.cordenete@gmail.com", "Aljon")

        `when`(authRepository
            .login(
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(session))

        viewModel.login(inputEmail, inputPassword)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(LoginState::class.java).run {
            Mockito.verify(observer, Mockito.times(1)).onNext(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `if input fields are empty, should expect FieldsAreEmpty state`() {
        val expected = LoginState.FieldsAreEmpty

        val inputEmail = ""
        val inputPassword = ""

        `when`(authRepository
            .login(
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(Session()))

        viewModel.login(inputEmail, inputPassword)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(LoginState::class.java).run {
            verify(observer, times(1)).onNext(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `if input email is empty, should expect EmailIsEmpty state`() {
        val expected = LoginState.EmailIsEmpty

        val inputEmail = ""
        val inputPassword = "password"

        `when`(authRepository
            .login(
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(Session()))

        viewModel.login(inputEmail, inputPassword)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(LoginState::class.java).run {
            verify(observer, times(1)).onNext(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `if input email is invalid, should expect EmailIsInvalid state`() {
        val expected = LoginState.EmailIsInvalid

        val inputEmail = "invalid"
        val inputPassword = "password"

        `when`(authRepository
            .login(
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(Session()))

        viewModel.login(inputEmail, inputPassword)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(LoginState::class.java).run {
            verify(observer, times(1)).onNext(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `if input password is empty, should expect PasswordIsEmpty state`() {
        val expected = LoginState.PasswordIsEmpty

        val inputEmail = "test@test.com"
        val inputPassword = ""

        `when`(authRepository
            .login(
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(Session()))

        viewModel.login(inputEmail, inputPassword)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(LoginState::class.java).run {
            verify(observer, times(1)).onNext(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `if input password is invalid, should expect PasswordIsInvalid state`() {
        val expected = LoginState.PasswordIsInvalid

        val inputEmail = "test@test.com"
        val inputPassword = "pass"

        `when`(authRepository
            .login(
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(Session()))

        viewModel.login(inputEmail, inputPassword)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(LoginState::class.java).run {
            verify(observer, times(1)).onNext(capture())
            assertEquals(expected, value)
        }
    }
}
