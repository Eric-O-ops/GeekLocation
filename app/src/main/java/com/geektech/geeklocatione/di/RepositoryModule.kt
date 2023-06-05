package com.geektech.geeklocatione.di

import com.geektech.data.repositories.location.LocAllUsersRepImpl
import com.geektech.data.repositories.location.LocThisUserRepImpl
import com.geektech.data.repositories.signin.SafeUserDataRepImpl
import com.geektech.data.repositories.signin.SignInRepImpl
import com.geektech.domain.repositories.location.LocAllUsersRep
import com.geektech.domain.repositories.location.LocThisUserRep
import com.geektech.domain.repositories.signin.SaveUserDataRep
import com.geektech.domain.repositories.signin.SignInRep
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

    @Binds
    fun bindLocThisUserRep(repository: LocThisUserRepImpl): LocThisUserRep

    @Binds
    fun bindLocAllUsersRep(repository: LocAllUsersRepImpl): LocAllUsersRep

}
