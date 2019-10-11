package com.roaim.togo.data.local

import androidx.lifecycle.LiveData
import com.roaim.togo.data.model.ToGo
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: ToGoDao) {
    suspend fun saveToGo(toGo: ToGo) = dao.saveToGo(toGo)

    fun getAllToGo(): LiveData<List<ToGo>> = dao.getAllToGo()

    fun getToGo(id: Int): LiveData<ToGo> = dao.getToGo(id)
}