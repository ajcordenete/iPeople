package com.aljon.ipeople.di.builders

import com.aljon.ipeople.features.person.list.PersonListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector()
    abstract fun contributePersonListFragment(): PersonListFragment
}
