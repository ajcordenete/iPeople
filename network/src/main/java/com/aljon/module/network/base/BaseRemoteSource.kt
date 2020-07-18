package com.aljon.module.network.base

import okhttp3.MediaType
import okhttp3.RequestBody

open class BaseRemoteSource {
    fun getJsonRequestBody(jsonString: String) =
        RequestBody
            .create(
                MediaType.parse("application/json"),
                jsonString
            )
}
