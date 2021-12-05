package com.ddona.l2011service

import android.Manifest
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ddona.l2011service.databinding.ActivityMusicBinding
import com.ddona.l2011service.media.MediaManager
import com.ddona.l2011service.service.DemoBindService
import com.ddona.l2011service.service.MusicService

class MusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicBinding
    private lateinit var musicService: MusicService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startMusicService()
        binding.btnNext.setOnClickListener {
            musicService.nextSong()
        }
        binding.btnPlayPause.setOnClickListener {
            musicService.playPauseSong()
        }
        binding.btnPrevious.setOnClickListener {
            musicService.previousSong()
        }
    }

    private fun startIntentService() {
        val intent = Intent(this, DemoBindService::class.java)
        intent.action = "Download"
        startService(intent)
    }


    private fun startMusicService() {
        val intent = Intent(this, MusicService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        bindService(intent, connection, BIND_AUTO_CREATE)
    }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicService = binder.getMusicService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }
}