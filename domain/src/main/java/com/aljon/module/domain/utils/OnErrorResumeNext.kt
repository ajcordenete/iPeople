package com.aljon.module.domain.utils

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

/**
 * Use this Transformer when you want to return specific object when a specific exception is thrown.
 *
 * @param S stream class type.
 * @param T exception class type to catch. (i.e: EmptyResultSetException)
 * @param errorObject the object to return when exception is thrown.
 * @param throwableToCatchClass exception class to catch. (i.e: EmptyResultSetException::class)
 */
class OnErrorResumeNext<S : Any, T : Throwable>(
    /**
     * Object to return when query is empty.
     */
    private val errorObject: S,
    /**
     * Exception class to catch. (i.e: EmptyResultSetException::class.java)
     */
    private val throwableToCatchClass: Class<T> // Added this since we cannot check generic's class type.
) : SingleTransformer<S, S> {

    override fun apply(upstream: Single<S>): SingleSource<S> {
        return upstream
            .onErrorResumeNext { throwable ->
                if (shouldCatchException(throwable)) {
                    return@onErrorResumeNext Single.just(errorObject)
                }
                return@onErrorResumeNext Single.error(throwable)
            }
    }

    private fun shouldCatchException(throwableThrown: Throwable): Boolean {
        return throwableToCatchClass == throwableThrown.javaClass
    }
}