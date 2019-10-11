package com.roaim.togo.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.roaim.togo.databinding.FragmentDashboardBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DashboardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        dashboardViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        return FragmentDashboardBinding.inflate(inflater).run {
            val adapter = DashBoardAdapter()
            recyclerView.adapter = adapter
            dashboardViewModel.togoList.observe(viewLifecycleOwner, Observer {
                adapter.reload(it)
            })
            root
        }
    }
}