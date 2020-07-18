package com.aljon.module.network.base.response

open class BaseResponse(val success: Boolean = false, val message: String = "", val http_status: Int = 500)
