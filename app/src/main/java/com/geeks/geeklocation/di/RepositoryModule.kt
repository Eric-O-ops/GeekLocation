package com.geeks.geeklocation.di

import com.geeks.data.repositories.location.LocAllUsersRepImpl
import com.geeks.data.repositories.location.LocThisUserRepImpl
import com.geeks.data.repositories.location.LocationRequestRepImpl
import com.geeks.data.repositories.locationprovider.LocationProviderRepImpl
import com.geeks.data.repositories.preference.PreferenceRepImpl
import com.geeks.data.repositories.signin.CheckSignInRepImpl
import com.geeks.data.repositories.signin.SafeUserDataRepImpl
import com.geeks.data.repositories.signin.SignInRepImpl
import com.geeks.domain.repositories.location.LocAllUsersRep
import com.geeks.domain.repositories.location.LocThisUserRep
import com.geeks.domain.repositories.location.LocationRequestRep
import com.geeks.domain.repositories.locationprovider.LocationProviderRep
import com.geeks.domain.repositories.preference.PreferenceRep
import com.geeks.domain.repositories.signin.CheckSignInRep
import com.geeks.domain.repositories.signin.SaveUserDataRep
import com.geeks.domain.repositories.signin.SignInRep
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

    @Binds
    fun bindPreferenceRep(repository: PreferenceRepImpl): PreferenceRep

    @Binds
    fun bindCheckSignInRep(repository: CheckSignInRepImpl): CheckSignInRep

    @Binds
    fun bindLocationProviderRep(repository: LocationProviderRepImpl): LocationProviderRep

    @Binds
    fun bindLocationRequestRep(repository: LocationRequestRepImpl): LocationRequestRep
}
