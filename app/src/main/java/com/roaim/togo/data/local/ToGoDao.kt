package com.roaim.togo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.roaim.togo.data.model.ToGo

@Dao
interface ToGoDao {
    @Insert
    suspend fun saveToGo(toGo: ToGo)

    @Query("SELECT * FROM `ToGo`")
    fun getAllToGo(): LiveData<List<ToGo>>

    @Query("SELECT * FROM `ToGo` WHERE id = :id ORDER BY schedule")
    fun getToGo(id: Int): LiveData<ToGo>
}