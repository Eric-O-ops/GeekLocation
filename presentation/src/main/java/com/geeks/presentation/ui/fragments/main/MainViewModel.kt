package com.geeks.presentation.ui.fragments.main

import androidx.lifecycle.ViewModel
import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locThisUserUseCase: LocThisUserUseCase,
    private val locAllUsersUseCase: LocAllUsersUseCase,
    private val gpsEnabledUseCase: IsGPSEnabledUseCase,
    private val networkEnabledUseCase: IsNetworkEnabledUseCase,
    private val startLocationRequestUseCase: StartLocationRequestUseCase,
    private val stopLocationRequestUseCase: StopLocationRequestUseCase
) : ViewModel() {

    fun updateLoc(location: LocModelDomain) = locThisUserUseCase(location)

    fun fetchUsers(list:(ArrayList<LocModelDomain>) -> Unit) = locAllUsersUseCase(list)

    fun isGpsEnabled() = gpsEnabledUseCase() //todo observe?

    fun isNetworkEnable() = networkEnabledUseCase() //todo observe?

    fun startLocationRequest(location: (LocModelDomain) -> Unit) =
        startLocationRequestUseCase(location)

    fun stopLocationRequest() = stopLocationRequestUseCase()
}