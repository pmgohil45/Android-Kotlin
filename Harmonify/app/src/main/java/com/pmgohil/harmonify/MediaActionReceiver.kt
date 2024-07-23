package com.pmgohil.harmonify

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MediaActionReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_PLAY = "com.pmgohil.harmonify.ACTION_PLAY"
        const val ACTION_PAUSE = "com.pmgohil.harmonify.ACTION_PAUSE"
        const val ACTION_NEXT = "com.pmgohil.harmonify.ACTION_NEXT"
        const val ACTION_PREVIOUS = "com.pmgohil.harmonify.ACTION_PREVIOUS"

        fun getActionIntent(context: Context, action: String): PendingIntent {
            val intent = Intent(context, MediaActionReceiver::class.java).setAction(action)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_PLAY -> MediaManager.resume()
            ACTION_PAUSE -> MediaManager.pause()
            ACTION_NEXT -> MediaManager.playNext()
            ACTION_PREVIOUS -> MediaManager.playPrevious()
            // Add more actions if needed
        }
    }
}
