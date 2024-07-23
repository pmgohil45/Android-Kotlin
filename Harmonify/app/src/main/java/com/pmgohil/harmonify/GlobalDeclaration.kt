package com.pmgohil.harmonify

import android.content.Intent

object GlobalDeclaration {
    lateinit var songTitle: String
    lateinit var songArtist: String

    //lateinit var songURL: String
    var songIndex: Int = 0

    //    var currentSong: String = null.toString()
    var isPlaying: Boolean = false

    var requestCode: Int = 0
    var resultCode: Int = 0
    var data: Intent? = null
}
