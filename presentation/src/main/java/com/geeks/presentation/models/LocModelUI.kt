package com.geeks.presentation.models

import com.geeks.domain.models.LocModelDomain

data class LocModelUI(
    val name: String? = null,
    val latitude: Double,
    val longitude: Double,
    val bearing: Float? = null
)

fun LocModelDomain.toUI() = LocModelUI(name, latitude, longitude, bearing)
fun LocModelUI.toDomain() = bearing?.let { LocModelDomain(name, latitude, longitude, it) }