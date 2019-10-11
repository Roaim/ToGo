package com.roaim.togo.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.roaim.togo.data.ToGoRepository
import com.roaim.togo.data.model.ToGo
import javax.inject.Inject

class DashboardViewModel @Inject constructor(repository: ToGoRepository) : ViewModel() {

    val togoList: LiveData<List<ToGo>> = repository.getSavedAddressees()

}