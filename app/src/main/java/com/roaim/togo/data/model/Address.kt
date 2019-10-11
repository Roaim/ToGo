package com.roaim.togo.data.model

import com.google.android.gms.maps.model.LatLng

data class Address(
    val name: String,
    val lat: Double,
    val lng: Double
) {
    fun getLatLng(): LatLng = LatLng(lat, lng)
}