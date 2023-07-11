package com.geeks.domain.repositories.location

import com.geeks.domain.models.LocModelDomain

interface LocThisUserRep {

    fun updateLoc(location: LocModelDomain)
}