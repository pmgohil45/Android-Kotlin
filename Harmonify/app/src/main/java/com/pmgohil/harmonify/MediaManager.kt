package com.pmgohil.harmonify

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.core.app.NotificationManagerCompat


object MediaManager {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var appContext: Context
    private lateinit var currentSongURL: String
    private lateinit var currentSongTitle: String
    private lateinit var currentSongArtist: String

    fun initializePlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
            setOnPreparedListener {
                start()
                GlobalDeclaration.isPlaying = true
                updateNotification()
            }
            setOnCompletionListener {
                GlobalDeclaration.isPlaying = false
                updateNotification()
            }
        }
    }

    fun play(url: String, title: String, artist: String) {
        currentSongURL = url
        currentSongTitle = title
        currentSongArtist = artist
        mediaPlayer?.apply {
            reset()
            setDataSource(url)
            prepareAsync()
        }
    }

    fun pause() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
                GlobalDeclaration.isPlaying = false
                updateNotification()
            }
        }
    }

    fun resume() {
        mediaPlayer?.apply {
            if (!isPlaying) {
                start()
                GlobalDeclaration.isPlaying = true
                updateNotification()
            }
        }
    }

    fun playNext() {
        // Implement logic to play the next song
        updateNotification()
    }

    fun playPrevious() {
        // Implement logic to play the previous song
        updateNotification()
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification() {
        val notification = NotificationHelper.showNotification(
            appContext, currentSongTitle, currentSongArtist, GlobalDeclaration.isPlaying
        )
        NotificationManagerCompat.from(appContext)
            .notify(NotificationHelper.NOTIFICATION_ID, notification)
    }
}
