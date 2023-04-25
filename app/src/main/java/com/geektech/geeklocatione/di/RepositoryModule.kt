package com.geektech.geeklocatione.di

import com.geektech.data.repositories.SafeUserDataRepImpl
import com.geektech.data.repositories.SignInRepImpl
import com.geektech.domain.repositories.SaveUserDataRep
import com.geektech.domain.repositories.SignInRep
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

 @Binds
    fun bindSignInRep(repository: SignInRepImpl): SignInRep

    @Binds
    fun bindSaveUserDataRepository(repository: SafeUserDataRepImpl): SaveUserDataRep

}
