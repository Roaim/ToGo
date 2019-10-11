package com.roaim.togo.data

import androidx.lifecycle.LiveData
import com.roaim.togo.data.local.LocalDataSource
import com.roaim.togo.data.model.ToGo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToGoRepository @Inject constructor(private val localDataSource: LocalDataSource) {
    fun getSavedAddressees(): LiveData<List<ToGo>> = localDataSource.getAllToGo()
}