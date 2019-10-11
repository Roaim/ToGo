package com.roaim.togo.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.roaim.togo.data.ToGoRepository
import com.roaim.togo.data.model.ToGo
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(private val toGoRepository: ToGoRepository) : ViewModel() {
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val latLng = MutableLiveData<LatLng>()

    fun getMarkerAddress(latLng: LatLng) = viewModelScope.launch {

    }

    fun getSavedMarkerAddress(): LiveData<List<ToGo>> = toGoRepository.getSavedAddressees()

    fun showMsg(msg: String) {
        _msg.value = msg
    }
}