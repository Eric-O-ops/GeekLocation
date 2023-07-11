package com.geeks.data.models

import com.geeks.domain.models.LocModelDomain

data class LocModelData(
    val name: String? = null,
    val latitude: Double,
    val longitude: Double,
    val bearing: Float
)

fun LocModelData.toDomain() = LocModelDomain(name, latitude, longitude, bearing)