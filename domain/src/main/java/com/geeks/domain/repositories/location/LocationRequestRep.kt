package com.geeks.domain.repositories.location

import com.geeks.domain.models.LocModelDomain

interface LocationRequestRep {

    fun start(location:(LocModelDomain) -> Unit)

    fun stop()
}