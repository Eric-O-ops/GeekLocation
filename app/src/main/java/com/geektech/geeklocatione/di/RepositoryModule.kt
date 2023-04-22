package com.geektech.geeklocation.di

import com.geektech.data.repositories.SafeUserDataRepositoryImpl
import com.geektech.data.repositories.SignInWithGoogleRepositoryImpl
import com.geektech.domain.repositories.SaveUserDataRepository
import com.geektech.domain.repositories.SignInWithGoogleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindSignInWithGoogleRepository(repository: SignInWithGoogleRepositoryImpl)
            : SignInWithGoogleRepository

    @Binds
    fun bindSaveUserDataRepository(repository: SafeUserDataRepositoryImpl)
            : SaveUserDataRepository
}