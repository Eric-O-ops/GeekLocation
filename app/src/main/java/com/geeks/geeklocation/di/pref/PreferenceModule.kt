package com.geeks.geeklocation.di.pref

import android.content.Context
import com.geeks.data.preferences.PreferencesHelper
import com.geeks.data.preferences.userdata.UserPreferencesData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Singleton
    @Provides
    fun providePreferenceHelper(@ApplicationContext context: Context) =
        PreferencesHelper(context)

    @Singleton
    @Provides
    fun provideUserPreferenceData(preferencesHelper: PreferencesHelper) =
        UserPreferencesData(preferencesHelper)

}