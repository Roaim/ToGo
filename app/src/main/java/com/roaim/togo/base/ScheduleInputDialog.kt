package com.roaim.togo.base

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AlertDialog
import com.roaim.togo.data.model.Address
import com.roaim.togo.data.model.ToGo
import com.roaim.togo.databinding.DialogScheduleInputBinding
import java.util.*

object ScheduleInputDialog {
    fun show(activity: Activity, address: Address, callback: (ToGo) -> Unit) =
        DialogScheduleInputBinding.inflate(activity.layoutInflater).run {
            AlertDialog.Builder(activity)
                .setView(root)
                .setCancelable(true)
                .setPositiveButton("Schedule") { _, _ ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, datePicker.year)
                    calendar.set(Calendar.MONTH, datePicker.month)
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                    calendar.set(
                        Calendar.HOUR,
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) timePicker.hour else timePicker.currentHour
                    )
                    calendar.set(
                        Calendar.MINUTE,
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) timePicker.minute else timePicker.currentMinute
                    )
                    callback(ToGo(address = address, schedule = calendar.timeInMillis))
                }
                .show()
        }

}