package com.roaim.togo.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import java.text.SimpleDateFormat
import java.util.*

fun LatLng.addMarker(
    map: GoogleMap,
    title: String? = null,
    subTitle: String? = null,
    icon: BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(),
    draggable: Boolean = false
): Marker? {
    return map.addMarker(
        MarkerOptions()
            .position(this)
            .title(title ?: "No title")
            .draggable(draggable)
            .icon(icon).apply {
                subTitle?.let {
                    snippet(it)
                }
            }
    )
}

fun LatLng.animateCamera(map: GoogleMap, zoom: Float) {
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(this, zoom))
}

fun Long.formatDate(): String = Date(this).let {
    SimpleDateFormat.getDateTimeInstance().format(it)
}