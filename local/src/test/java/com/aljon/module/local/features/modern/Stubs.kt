package com.aljon.module.local.features.modern

import com.aljon.module.domain.features.auth.models.Session
import com.aljon.module.local.features.modern.auth.models.DBUserSession

object Stubs {
    val DB_USER_SESSION = DBUserSession(
        pk_id = 1231232132123,
        fullName = "Jose Mari Chan",
        firstName = "Jose Mari",
        lastName = "Chan",
        email = "josemarichan@gmail.com",
        avatarPermanentThumbUrl = "http://www.google.com/",
        avatarPermanentUrl = "http://www.google.com/",
        phoneNumber = "+639435643214",
        emailVerified = true,
        phoneNumberVerified = true,
        verified = true,
        uid = "1231232132123"
    )

    val EMPTY_DB_USER_SESSION = DBUserSession.empty()

    val USER_SESSION = Session(
        id = "1231232132123",
        fullName = "Jose Mari Chan",
        firstName = "Jose Mari",
        lastName = "Chan",
        email = "josemarichan@gmail.com",
        avatarPermanentThumbUrl = "http://www.google.com/",
        avatarPermanentUrl = "http://www.google.com/",
        phoneNumber = "+639435643214",
        emailVerified = true,
        phoneNumberVerified = true,
        verified = true
    )
}
