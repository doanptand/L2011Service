package com.ddona.l2011service.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.ddona.l2011service.R
import com.ddona.l2011service.media.MediaManager

class MusicService : Service() {


    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaManager.playPauseSong()
        runForeground()
        return START_STICKY
    }

    private fun runForeground() {
        val builder = Notification.Builder(this)
        builder.setContentText("This is music app")
        builder.setContentTitle("Music")
        builder.setSmallIcon(R.mipmap.ic_launcher)

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

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}