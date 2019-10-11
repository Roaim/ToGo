package com.roaim.togo.di.module

import com.roaim.togo.ui.dashboard.DashboardFragment
import com.roaim.togo.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment
}