package com.roaim.togo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roaim.togo.data.ToGoRepository
import com.roaim.togo.data.model.ToGo
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: ToGoRepository) : ViewModel() {
    fun saveSchedule(toGo: ToGo) = viewModelScope.launch {
        repository.saveToGo(toGo)
    }
}