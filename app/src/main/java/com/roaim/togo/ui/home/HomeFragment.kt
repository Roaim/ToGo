package com.roaim.togo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.roaim.togo.R
import com.roaim.togo.base.ScheduleInputDialog
import com.roaim.togo.ui.map.MapHelper
import com.roaim.togo.ui.map.MapViewModel
import com.roaim.togo.utils.animateCamera
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
        MapHelper(this, mapViewModel).getMap {
            LatLng(23.7944856, 90.3985731).animateCamera(it, 13f)
        }
        mapViewModel.poi.observe(viewLifecycleOwner, Observer { addr ->
            Snackbar.make(view, addr.name, Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Schedule") {
                    activity?.let { act ->
                        ScheduleInputDialog.show(act, addr) {
                            homeViewModel.saveSchedule(it)
                        }
                        dismiss()
                    }
                }
            }.show()
        })
    }
}