package com.geeks.domain.usecases

import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.repositories.location.LocAllUsersRep
import javax.inject.Inject

class LocAllUsersUseCase @Inject constructor(
    private val rep: LocAllUsersRep
) {

    operator fun invoke(list:(ArrayList<LocModelDomain>) -> Unit) = rep.fetchUsers(list)
}