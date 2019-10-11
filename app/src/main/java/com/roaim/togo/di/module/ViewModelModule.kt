package com.roaim.togo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roaim.togo.di.ViewModelKey
import com.roaim.togo.ui.ViewModelFactory
import com.roaim.togo.ui.dashboard.DashboardViewModel
import com.roaim.togo.ui.home.HomeViewModel
import com.roaim.togo.ui.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    protected abstract fun mapViewModel(mapViewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    protected abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    protected abstract fun dashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

}