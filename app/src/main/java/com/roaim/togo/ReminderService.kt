package com.roaim.togo

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Observer
import com.roaim.togo.data.local.ToGoDao
import com.roaim.togo.data.model.ToGo
import dagger.android.AndroidInjection
import javax.inject.Inject

class ReminderService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    @Inject
    lateinit var dao: ToGoDao

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val observer = Observer<List<ToGo>> {
            it.forEach { togo ->
                setReminder(this, togo)
            }
        }
        dao.getAllToGo().apply {
            observeForever(observer.also {
                removeObserver(it)
            })
        }
        return START_NOT_STICKY
    }

    fun setReminder(context: Context, togo: ToGo) {
        PlaceReminder.setReminder(context, togo)
        PlaceReminder.setReminder(
            context, ToGo(
                togo.id.times(10000),
                togo.address,
                togo.schedule.minus(24 * 60 * 60 * 1000)
            )
        )
    }
}