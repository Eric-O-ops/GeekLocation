package com.geektech.geeklocatione.di.locationprovider

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationProvider {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context
}