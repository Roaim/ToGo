package com.roaim.togo

import android.app.Application
import com.roaim.togo.di.DaggerTogoComponent
import com.roaim.togo.di.module.DbModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class ToGoApplication : Application(), HasAndroidInjector {
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerTogoComponent.builder()
            .application(this)
            .dbModule(DbModule())
            .build()
            .inject(this)
    }
}