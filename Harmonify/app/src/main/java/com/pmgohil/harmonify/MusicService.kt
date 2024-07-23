package com.pmgohil.harmonify

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder

class MusicService : Service() {

    override fun onCreate() {
        super.onCreate()
        // Initialize MediaPlayer when the service is created
        MediaManager.initializePlayer()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songUrl = intent?.getStringExtra("SONG_URL")
        val songTitle = intent?.getStringExtra("SONG_TITLE")
        val songArtist = intent?.getStringExtra("SONG_ARTIST")

        if (songUrl != null && songTitle != null && songArtist != null) {
            // Start media playback
            MediaManager.play(songUrl, songTitle, songArtist)

            // Show notification
            startForeground(NotificationHelper.NOTIFICATION_ID, createNotification())
        }
        return START_STICKY
    }

    private fun createNotification(): Notification {
        val isPlaying = GlobalDeclaration.isPlaying
        return NotificationHelper.showNotification(this, "Song Title", "Artist Name", isPlaying)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop media playback
        MediaManager.pause()
        stopForeground(true)
    }
}
