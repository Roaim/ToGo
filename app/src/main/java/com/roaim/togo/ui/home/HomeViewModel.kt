package com.roaim.togo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roaim.togo.data.ToGoRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: ToGoRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}