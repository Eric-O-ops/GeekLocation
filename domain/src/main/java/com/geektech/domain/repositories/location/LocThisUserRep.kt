package com.geektech.domain.repositories.location

import com.geektech.domain.modles.LocModel

interface LocThisUserRep {

    fun updateLoc(location: LocModel)
}