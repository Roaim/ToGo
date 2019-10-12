package com.roaim.togo.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
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
    private val args: HomeFragmentArgs by navArgs()

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
        val mapHelper = MapHelper(this, mapViewModel)
        mapHelper.getMap {
            LatLng(args.lat.toDouble(), args.lng.toDouble()).apply {
                animateCamera(it, 13f)
                mapHelper.showInfoWindow(this)
            }
        }
        mapViewModel.poi.observe(viewLifecycleOwner, Observer { addr ->
            mapHelper.addMarker(addr.name, addr.getLatLng())
            Snackbar.make(view, addr.name, Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Schedule") {
                    activity?.let { act ->
                        ScheduleInputDialog.show(act, addr) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                showDozeOptimizationDialog(act)
                            }
                            homeViewModel.saveSchedule(it)
                        }
                        dismiss()
                    }
                }
            }.show()
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDozeOptimizationDialog(activity: Activity) {
        val packageName = activity.packageName
        val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent()
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packageName")
            activity.startActivity(intent)
        }
    }
}