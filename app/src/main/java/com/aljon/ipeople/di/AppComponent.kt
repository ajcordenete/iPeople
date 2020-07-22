package com.aljon.ipeople.di

import android.app.Application
import com.aljon.ipeople.IpeopleApplication
import com.aljon.ipeople.di.builders.ActivityBuilder
import com.aljon.ipeople.di.builders.FragmentBuilder
import com.aljon.module.local.StorageModule
import com.aljon.module.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import com.aljon.module.data.mapper.MapperModule
import com.aljon.module.local.DatabaseModule

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            MapperModule::class,
            StorageModule::class,
            DatabaseModule::class,
            NetworkModule::class,
            RepositoryModule::class,
            ActivityBuilder::class,
            FragmentBuilder::class,
            SchedulerModule::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: IpeopleApplication)
}
