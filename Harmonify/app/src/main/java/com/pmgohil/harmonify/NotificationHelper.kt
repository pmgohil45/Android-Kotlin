package com.pmgohil.harmonify

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat.MediaStyle

object NotificationHelper {
    private const val CHANNEL_ID = "music_playback_channel"
    const val NOTIFICATION_ID = 1

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Music Playback"
            val descriptionText = "Controls for music playback"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun showNotification(
        context: Context,
        audioTitle: String,
        artist: String,
        isPlaying: Boolean
    ): Notification {
        createNotificationChannel(context)

        val mediaSession = MediaSessionCompat(context, "tag")

        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(
                R.drawable.ic_pause,
                "Pause",
                MediaActionReceiver.getActionIntent(context, MediaActionReceiver.ACTION_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.ic_play,
                "Play",
                MediaActionReceiver.getActionIntent(context, MediaActionReceiver.ACTION_PLAY)
            )
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(audioTitle)
            .setContentText(artist)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.side_nav_bar))
            .addAction(
                R.drawable.ic_previous,
                "Previous",
                MediaActionReceiver.getActionIntent(context, MediaActionReceiver.ACTION_PREVIOUS)
            )
            .addAction(playPauseAction)
            .addAction(
                R.drawable.ic_next,
                "Next",
                MediaActionReceiver.getActionIntent(context, MediaActionReceiver.ACTION_NEXT)
            )
            .setStyle(
                MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true)
            .build()
    }
}