package com.roaim.togo.di


import android.app.Application
import com.roaim.togo.ToGoApplication
import com.roaim.togo.di.module.ActivityModule
import com.roaim.togo.di.module.DbModule
import com.roaim.togo.di.module.FragmentModule
import com.roaim.togo.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

import javax.inject.Singleton

@Singleton
@Component(
    modules = [DbModule::class,
        ViewModelModule::class, AndroidSupportInjectionModule::class,
        ActivityModule::class, FragmentModule::class]
)
interface TogoComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun dbModule(dbModule: DbModule): Builder

        fun build(): TogoComponent
    }

    fun inject(toGoApplication: ToGoApplication)
}
