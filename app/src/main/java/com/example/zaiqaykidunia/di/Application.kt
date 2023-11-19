package com.example.zaiqaykidunia.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {
        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }

}