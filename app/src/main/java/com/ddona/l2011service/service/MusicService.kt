package com.ddona.l2011service.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ddona.l2011service.R
import com.ddona.l2011service.media.MediaManager
import com.ddona.l2011service.receiver.FirstReceiver
import com.ddona.l2011service.receiver.LocalReceiver
import com.ddona.l2011service.receiver.SecondReceiver

class MusicService : Service() {

    private val binder = MusicBinder()
    private val firstReceiver = FirstReceiver()
    private val secondReceiver = SecondReceiver()
    private val localReceiver = LocalReceiver()

    override fun onCreate() {
        super.onCreate()
        val firstFilter = IntentFilter()
        firstFilter.addAction("android.intent.action.SCREEN_ON")
        firstFilter.addAction(Intent.ACTION_SCREEN_ON)
        firstFilter.addAction(Intent.ACTION_SCREEN_OFF)
        firstFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        firstFilter.addAction("com.ddona.deptrai")
        firstFilter.priority = 999
        registerReceiver(firstReceiver, firstFilter)

        val secondFilter = IntentFilter()
        secondFilter.addAction("android.intent.action.SCREEN_ON")
        secondFilter.addAction(Intent.ACTION_SCREEN_ON)
        secondFilter.addAction(Intent.ACTION_SCREEN_OFF)
        secondFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        secondFilter.addAction("com.ddona.deptrai")
        secondFilter.priority = 999
        registerReceiver(secondReceiver, secondFilter)

        val localFilter = IntentFilter()
        localFilter.addAction(Intent.ACTION_SCREEN_ON)
        localFilter.addAction(Intent.ACTION_SCREEN_OFF)
        localFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        localFilter.addAction("com.ddona.deptrai")
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, localFilter)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class MusicBinder : Binder() {

        fun getMusicService(): MusicService {
            return this@MusicService
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaManager.playPauseSong()
        runForeground()
        return START_STICKY
    }

    private fun runForeground() {
        val notificationView = RemoteViews(packageName, R.layout.layout_notification)


//        notificationView.setOnClickPendingIntent(R.id.ibNext, nextPendingIntent)


        val builder = Notification.Builder(this)
        builder.setContentText("This is music app")
        builder.setContentTitle("Music")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(notificationView)
        } else {
            builder.setContent(notificationView)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("123", "123", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channel.id)
        }

        val notification = builder.build()
//        notificationManager.notify(1, notification)
        startForeground(1, notification)
    }

    fun nextSong() {
        MediaManager.nextSong()
    }

    fun previousSong() {
        MediaManager.previousSong()
    }

    fun playPauseSong() {
        MediaManager.playPauseSong()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        unregisterReceiver(firstReceiver)
        super.onDestroy()
    }
}