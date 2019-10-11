package com.roaim.togo.ui.map

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.observe
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import com.roaim.togo.R
import com.roaim.togo.utils.addMarker
import com.roaim.togo.utils.animateCamera
import com.roaim.togo.utils.formatDate
import java.lang.ref.WeakReference

class MapHelper(parent: Fragment, private val viewModel: MapViewModel, resId: Int = R.id.map) :
    LifecycleObserver,
    OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnPoiClickListener {

    private val lifecycleOwner = WeakReference(parent.viewLifecycleOwner);

    private var mapCallback: ((GoogleMap) -> Unit)? = null
    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null

    init {
        lifecycleOwner.get()?.lifecycle?.addObserver(this)
        val mapFragment = parent.childFragmentManager.findFragmentById(resId) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun getMap(block: (GoogleMap) -> Unit = {}) =
        if (map == null) {
            this.mapCallback = block
        } else block(map!!)

    // Map ready callback
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map?.setOnMarkerDragListener(this)
        map?.setOnPoiClickListener(this)
        mapCallback?.invoke(p0)
        if (lifecycleOwner.get() == null) return
        viewModel.getSavedMarkerAddress().observe(lifecycleOwner.get()!!) {
            it.forEach { togo ->
                togo.address.apply {
                    getLatLng().addMarker(
                        p0,
                        name,
                        togo.schedule.formatDate(),
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                }
            }
        }
    }

    //    POI click listener
    override fun onPoiClick(p0: PointOfInterest?) {
        map?.also {
            currentMarker?.remove()
            currentMarker =
                p0?.latLng?.addMarker(it, p0.name)
            p0?.latLng?.animateCamera(it, 18f)
        }
        viewModel.showMsg("POI name = ${p0?.name}")
    }

    // Marker drag listener start
    override fun onMarkerDrag(p0: Marker?) {}

    override fun onMarkerDragStart(p0: Marker?) {}

    override fun onMarkerDragEnd(p0: Marker?) {
        map?.also {
            p0?.position?.animateCamera(it, 18f)
        }
    }
    // Marker drag listener end

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        map?.setOnMarkerDragListener(null)
        map?.setOnMapLongClickListener(null)
        mapCallback = null
        map = null
        currentMarker = null
    }
}