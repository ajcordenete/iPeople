package com.aljon.module.local.features.modern.auth

import androidx.room.EmptyResultSetException
import com.aljon.module.local.features.modern.AppDatabase
import com.aljon.module.local.features.modern.Stubs
import com.aljon.module.local.features.modern.auth.dao.UserDao
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AuthLocalSourceImplTest {

    private val appDatabase: AppDatabase = mock(AppDatabase::class.java)
    private val userDao: UserDao = mock(UserDao::class.java)
    private lateinit var authLocalSource: AuthLocalSource

    @Before
    fun setUp() {
        authLocalSource = AuthLocalSourceImpl(appDatabase)
    }

    @Test
    fun getUser_ShouldReturnEmptyUser_WhenQueryIsEmpty() {
        val expected = Stubs.EMPTY_DB_USER_SESSION.fullName

        `when`(appDatabase.userSessionDao()).thenReturn(userDao)
        `when`(userDao.getUserInfo())
            .thenReturn(
                Single.error(EmptyResultSetException("Empty query."))
            )

        authLocalSource
            .getUserSession()
            .test()
            .assertValue { it.fullName == expected }
    }

    @Test
    fun saveCredentials_ShouldLogoutPreviousUser() {
        `when`(appDatabase.userSessionDao()).thenReturn(userDao)

        val user = Stubs.USER_SESSION

        authLocalSource
            .saveCredentials(user)
            .test()

        verify(userDao, times(1)).logoutUser()
    }
}
