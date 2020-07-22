package com.aljon.ipeople.core

import com.aljon.ipeople.utils.schedulers.BaseSchedulerProvider
import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: TestScheduler) : BaseSchedulerProvider {
    override fun computation(): Scheduler = scheduler

    override fun io(): Scheduler = scheduler

    override fun ui(): Scheduler = scheduler

    override fun <T> applySchedulers(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(io()).observeOn(ui())
    }

    override fun <T> applyMaybeSchedulers(): MaybeTransformer<T, T> {
        TODO("not implemented")
    }

    override fun applyCompletableSchedulers(): CompletableTransformer {
        TODO("not implemented")
    }
}
