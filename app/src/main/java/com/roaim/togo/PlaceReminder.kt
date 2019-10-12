package com.roaim.togo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.roaim.togo.data.model.ToGo
import com.roaim.togo.utils.formatDate

class PlaceReminder : BroadcastReceiver() {

    companion object {
        private const val PARAM_ID = "id"
        private const val PARAM_NAME = "name"
        private const val PARAM_TIME = "time"

        fun setReminder(context: Context, toGo: ToGo) {
            val pendingIntent =
                getPendingIntent(context, toGo)
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarm.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    toGo.schedule,
                    pendingIntent
                )
            } else {
                alarm.setExact(AlarmManager.RTC_WAKEUP, toGo.schedule, pendingIntent)
            }
        }

        private fun getPendingIntent(context: Context, toGo: ToGo) =
            Intent(context, PlaceReminder::class.java).let {
                it.putExtra(PARAM_NAME, toGo.address.name)
                it.putExtra(PARAM_TIME, toGo.schedule.formatDate())
                it.putExtra(PARAM_ID, toGo.id)
                PendingIntent.getBroadcast(context, toGo.id, it, 0)
            }

        fun cancelReminder(context: Context, toGo: ToGo) {
            val pendingIntent =
                getPendingIntent(context, toGo)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }

        fun enableBootReceiver(context: Context) {
            val receiver = ComponentName(context, PlaceReminder::class.java)

            context.packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }


    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            context.startService(Intent(context, ReminderService::class.java))
        } else {
            try {
                val id = intent.getIntExtra(PARAM_ID, 0)
                val name = intent.getStringExtra(PARAM_NAME) ?: "No title"
                val time = intent.getStringExtra(PARAM_TIME) ?: "No time"
                showNotification(context, id, name, time)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showNotification(context: Context, id: Int, name: String, time: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val builder = NotificationCompat.Builder(context, name)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(name)
            .setContentText(time)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(id, builder.build())

    }
}