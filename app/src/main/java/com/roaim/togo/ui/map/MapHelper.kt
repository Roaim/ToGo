package com.roaim.togo.ui.map

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.observe
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.roaim.togo.R
import com.roaim.togo.data.model.Address
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

    init {
        if (!Places.isInitialized() && parent.activity != null) {
            Places.initialize(
                parent.activity!!.applicationContext,
                parent.getString(R.string.google_maps_key)
            );
        }
        val autocompleteFragment =
            parent.childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) =
                Toast.makeText(parent.context, p0.statusMessage, Toast.LENGTH_LONG).show()

            override fun onPlaceSelected(place: Place) {
                place.latLng?.apply {
                    viewModel.setSchedule(
                        Address(
                            place.name ?: "No name",
                            latitude,
                            longitude
                        )
                    )
                }
            }
        })
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

    fun addMarker(name: String, latLng: LatLng) {
        map?.also {
            currentMarker?.remove()
            currentMarker = latLng.addMarker(it, name)
            latLng.animateCamera(it, 18f)
        }
    }

    //    POI click listener
    override fun onPoiClick(p0: PointOfInterest) {
        viewModel.setSchedule(Address(p0.name, p0.latLng.latitude, p0.latLng.longitude))
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