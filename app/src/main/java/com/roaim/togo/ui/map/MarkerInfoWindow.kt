package com.roaim.togo.ui.map

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoWindow(private var fragment: Fragment?) : GoogleMap.InfoWindowAdapter,
    LifecycleObserver {

    init {
        fragment?.viewLifecycleOwner?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {

    }

    override fun getInfoContents(p0: Marker?): View? {

        return null
    }

    override fun getInfoWindow(p0: Marker?) = null
}