package com.roaim.togo.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.roaim.togo.data.local.ToGoDB
import com.roaim.togo.data.local.ToGoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): ToGoDB {
        return Room.databaseBuilder(
            application, ToGoDB::class.java, "togo.db"
        ).build()
    }

    @Provides
    @Singleton
    internal fun provideTogoDao(toGoDB: ToGoDB): ToGoDao {
        return toGoDB.toGoDao()
    }

    @Provides
    @Singleton
    internal fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences("config", Context.MODE_PRIVATE)
    }
}