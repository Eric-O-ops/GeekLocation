package com.geeks.domain.repositories.location

import com.geeks.domain.models.LocModelDomain

interface LocAllUsersRep {

    fun fetchUsers(list:(ArrayList<LocModelDomain>) -> Unit)
}