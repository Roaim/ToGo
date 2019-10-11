package com.roaim.togo.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roaim.togo.utils.formatDate

@Entity
data class ToGo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded(prefix = "address_")
    val address: Address,
    val schedule: Long
) {
    fun getFormattedDate() = schedule.formatDate()
}