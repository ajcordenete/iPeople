package com.aljon.module.network.base.response.error

import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import org.json.JSONObject

class ResponseError {

    companion object {
        fun getError(throwable: Throwable, callback: ErrorCallback) {
            when (throwable) {
                is HttpException -> {
                    val responseBody = throwable.response().errorBody()
                    responseBody?.let {
                        callback.httpExceptionCallback?.invoke(getErrorMessage(it))
                    }
                }
                is SocketTimeoutException -> callback.timeoutCallback?.invoke(throwable.message!!)
                is IOException -> callback.ioExceptionCallback?.invoke(throwable.message!!)
                else -> callback.unknownErrorCallback?.invoke(throwable.message!!)
            }
        }

        private fun getErrorMessage(responseBody: ResponseBody): String {
            return try {
                val jsonObject = JSONObject(responseBody.string())
                return if (jsonObject.has("data") && jsonObject.get("data") is JSONArray) {
                    jsonObject.getString("message")
                } else if (jsonObject.has("message") && jsonObject.get("message") is JSONArray) {
                    // If message is a JsonArray
                    val messageArray = jsonObject.getJSONArray("message")
                    // Check if the array has items
                    if (messageArray.length() >= 1) {
                        // Get first item of array and return it
                        return messageArray[0] as String
                    }

                    // If array is empty, return empty string
                    ""
                } else {
                    if (jsonObject.has("data")) {
                        val dataObject = jsonObject.getJSONObject("data")
                        if (dataObject.length() > 0) {
                            if (dataObject.has("error")) {
                                return dataObject.getString("error")
                            } else {
                                val name = dataObject.names()?.get(0) as String
                                if (dataObject.has(name)) dataObject.getJSONArray(name)[0] as String else jsonObject.getString(
                                    "message"
                                )
                            }
                        }
                    }
                    jsonObject.getString("message")
                }
            } catch (e: Exception) {
                e.message!!
            }
        }
    }

    data class ErrorCallback(
        val httpExceptionCallback: ((String) -> Unit)? = null,
        val timeoutCallback: ((String) -> Unit)? = null,
        val ioExceptionCallback: ((String) -> Unit)? = null,
        val unknownErrorCallback: ((String) -> Unit)? = null
    )
}
