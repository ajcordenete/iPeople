package com.aljon.module.local.features.auth.mapper

import com.aljon.module.domain.features.auth.models.Session
import com.aljon.module.domain.features.auth.models.User

fun User.toSession(): Session {
    return with(this) {
        Session(
            id = id.toString(),
            name = name,
            email = email
        )
    }
}
