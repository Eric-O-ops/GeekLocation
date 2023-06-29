package com.geeks.domain.repositories.location

import com.geeks.domain.modles.LocModel

interface LocThisUserRep {

    fun updateLoc(location: LocModel)
}