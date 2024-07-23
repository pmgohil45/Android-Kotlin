package com.pmgohil.harmonify

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AudioFragment : Fragment(), AudioAdapter.OnItemClickListener,
    AudioManager.OnAudioFocusChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var audioAdapter: AudioAdapter
    private lateinit var searchBar: EditText
    private lateinit var songTitleTextView: TextView
    private lateinit var shuffleToggleButton: ToggleButton
    private lateinit var previousButton: ImageButton
    private lateinit var playPauseButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var playbackControls: CardView
    private lateinit var seekBar: SeekBar
    private lateinit var countdownTimerTextView: TextView
    private lateinit var totalTimerTextView: TextView

    private val audioList: MutableList<AudioDataClass> = mutableListOf()
    private var mediaPlayer: MediaPlayer? = null
    private var currentAudioIndex: Int = -1
    private var handler: Handler? = null
    private var isUserSeeking: Boolean = false
    private var audioManager: AudioManager? = null
    private var audioFocusRequested: Boolean = false
    private var isAppInBackground: Boolean = false
    private var wasPlayingBeforeFocusLoss: Boolean = false
    private lateinit var mediaSession: MediaSessionCompat
    private var isShuffleEnabled: Boolean = false

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    @SuppressLint("SetTextI18n", "MissingInflatedId", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_audio, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recommendations_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        audioAdapter = AudioAdapter(requireContext(), audioList, this)
        recyclerView.adapter = audioAdapter

        // Initialize AudioManager
        audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Initialize search bar
        searchBar = view.findViewById(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterAudio(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Initialize MediaSessionCompat
        mediaSession = MediaSessionCompat(requireContext(), "HarmonifyMediaSession")
        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )

        // Set callback for media buttons
        mediaSession.setCallback(MediaSessionCallback())

        songTitleTextView = view.findViewById(R.id.songTitle)
        playbackControls = view.findViewById(R.id.playbackControls)
        previousButton = view.findViewById(R.id.previousButton)
        playPauseButton = view.findViewById(R.id.playPauseButton)
        nextButton = view.findViewById(R.id.nextButton)
        seekBar = view.findViewById(R.id.seekBar)
        countdownTimerTextView = view.findViewById(R.id.countdownTimerTextView)
        totalTimerTextView = view.findViewById(R.id.totalTimerTextView)
        shuffleToggleButton = view.findViewById(R.id.shuffleToggleButton)

        shuffleToggleButton.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                // Shuffle is enabled
                playRandomSong()
            } else {
                // Shuffle is disabled
                playNext()
            }
        }

        songTitleTextView.isSelected = true

        // Request permission to access external storage
        if (isReadStoragePermissionGranted()) {
            loadAudioFiles()
        }
        return view
    }

    private fun playRandomSong() {
//        changeButtonColorTemporarily()
        val randomAudio = audioAdapter.getRandomAudio()
        if (randomAudio != null) {
            //setupMediaPlayer(randomAudio)
            playAudio(randomAudio)
        }

        audioAdapter.setCurrentlyPlayingIndex(currentAudioIndex)
        // Highlight the current song
        highlightCurrentSong()
    }

    /*private fun changeButtonColorTemporarily() {
        txt_shuffle.setTextColor(
            ContextCompat.getColor(
                requireContext(), R.color.highlighted_music_color
            )
        )
        handler?.postDelayed({
            txt_shuffle.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.text_color
                )
            )
        }, 500) // Change back to default color after 500ms
    }*/

    private fun filterAudio(query: String) {
        val filteredList = audioList.filter {
            it.title.contains(query, ignoreCase = true) || it.artist.contains(
                query, ignoreCase = true
            )
        }
        audioAdapter.updateList(filteredList)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isReadStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is granted
                return true
            } else {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
                return false
            }
        } else {
            // Permission is automatically granted on SDK < 23 upon installation
            return true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to load audio files
                loadAudioFiles()
            } else {
                // Permission denied
                Toast.makeText(
                    requireContext(),
                    "Permission Denied. Cannot load audio files.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("InlinedApi", "NotifyDataSetChanged")
    private fun loadAudioFiles() {
        // Querying media files from external storage
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0" + // Only music files
                " AND ${MediaStore.Audio.Media.IS_ALARM} == 0" +   // Exclude alarm sounds
                " AND ${MediaStore.Audio.Media.IS_NOTIFICATION} == 0" +  // Exclude notification sounds
                " AND ${MediaStore.Audio.Media.IS_RINGTONE} == 0" + // Exclude ringtone sounds
                " AND ${MediaStore.Audio.Media.IS_RECORDING} == 0" +// Exclude recprdomg sounds
                " AND NOT (${MediaStore.Audio.Media.DATA} LIKE '%AUD%' OR ${MediaStore.Audio.Media.TITLE} LIKE '%AUD%')"

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA // File path
        )
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"

        val cursor = requireContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, sortOrder
        )

        cursor?.use { c ->
            val idColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val pathColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            audioList.clear()
            while (c.moveToNext()) {
                val id = c.getLong(idColumn)
                val title = c.getString(titleColumn)
                val artist = c.getString(artistColumn)
                val path = c.getString(pathColumn)
                val audio = AudioDataClass(id, title, artist, path)
                audioList.add(audio)
            }
            highlightCurrentSong()
        }
        audioAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(audio: AudioDataClass) {
        playAudio(audio)
        //setupMediaPlayer(audio)
    }

    private fun setupMediaPlayer(audio: AudioDataClass) {
        playAudio(audio)

        searchBar.clearFocus()
        songTitleTextView.text = audio.title
        val artist = audio.artist
        playbackControls.visibility = View.VISIBLE

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audio.path)
            prepareAsync()
            setOnPreparedListener {
                startPlayback(it)
                GlobalDeclaration.isPlaying = isPlaying

                NotificationHelper.showNotification(
                    requireContext(),
                    songTitleTextView.text.toString(),
                    artist,
                    GlobalDeclaration.isPlaying
                )// Show notification

            }
            setOnCompletionListener {
                playNext()
            }
        }

        // Update current audio index
        //currentAudioIndex = audioList.indexOf(audio)

        // Update currently playing index in the adapter
        audioAdapter.setCurrentlyPlayingIndex(audioList.indexOf(audio))

        // Highlight the current song in the RecyclerView
        highlightCurrentSong()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    isUserSeeking = true
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler?.removeCallbacks(updateSeekBarRunnable)
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                handler?.postDelayed(updateSeekBarRunnable, 1000)
                isUserSeeking = false
            }
        })

        playPauseButton.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                playPauseButton.setImageResource(R.drawable.ic_play)
                NotificationHelper.showNotification(
                    requireContext(),
                    songTitleTextView.text.toString(),
                    audioList[currentAudioIndex].artist,
                    false
                )
            } else {
                mediaPlayer?.start()
                playPauseButton.setImageResource(R.drawable.ic_pause)
                NotificationHelper.showNotification(
                    requireContext(),
                    songTitleTextView.text.toString(),
                    audioList[currentAudioIndex].artist,
                    true
                )
            }
        }

        previousButton.setOnClickListener {
            playPrevious()
        }

        nextButton.setOnClickListener {
            playNext()
        }
    }

    private fun startPlayback(player: MediaPlayer) {
        player.start()
        seekBar.max = player.duration
        seekBar.progress = player.currentPosition
        handler = Handler()
        handler?.postDelayed(updateSeekBarRunnable, 1000)
        updateTimer(player.currentPosition)
        playPauseButton.setImageResource(R.drawable.ic_pause)

        // Request audio focus when playback starts
        requestAudioFocus()

        // Show notification
        NotificationHelper.showNotification(
            requireContext(),
            songTitleTextView.text.toString(),
            audioList[currentAudioIndex].artist,
            true
        )

    }

    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                val currentPosition = it.currentPosition
                seekBar.progress = currentPosition
                handler?.postDelayed(this, 1000)
                updateTimer(currentPosition)
            }
        }
    }

    private fun updateTimer(currentDuration: Int) {
        val remainingTime = mediaPlayer?.duration?.minus(currentDuration) ?: 0
        val totalMinutes = remainingTime / 1000 / 60
        val totalSeconds = remainingTime / 1000 % 60
        val currentMinutes = currentDuration / 1000 / 60
        val currentSeconds = currentDuration / 1000 % 60

        countdownTimerTextView.text = String.format("%02d:%02d", totalMinutes, totalSeconds)
        totalTimerTextView.text = String.format("%02d:%02d", currentMinutes, currentSeconds)
    }

    private fun playPrevious() {
        if (currentAudioIndex > 0) {
            currentAudioIndex--
            val previousAudio = audioList[currentAudioIndex]
            playAudio(previousAudio)
            audioAdapter.setCurrentlyPlayingIndex(currentAudioIndex)
        }
    }

    private fun playNext() {
        if (shuffleToggleButton.isChecked != isShuffleEnabled) {
            playRandomSong()
        } else {
            if (currentAudioIndex < audioList.size - 1) {
                currentAudioIndex++
                playAudio(audioList[currentAudioIndex])
                audioAdapter.setCurrentlyPlayingIndex(currentAudioIndex)
            }
        }
    }

    private fun playAudio(audio: AudioDataClass) {
        songTitleTextView.text = audio.title
        playbackControls.visibility = View.VISIBLE

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audio.path)
            prepareAsync()
            setOnPreparedListener {
                startPlayback(it)
            }
            setOnCompletionListener {
                playNext()
            }
        }
        currentAudioIndex = audioList.indexOf(audio)
        GlobalDeclaration.songIndex = audioList.indexOf(audio)

        searchBar.clearFocus()
        songTitleTextView.text = audio.title
        val artist = audio.artist
        playbackControls.visibility = View.VISIBLE

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audio.path)
            prepareAsync()
            setOnPreparedListener {
                startPlayback(it)
                GlobalDeclaration.isPlaying = isPlaying

                NotificationHelper.showNotification(
                    requireContext(),
                    songTitleTextView.text.toString(),
                    artist,
                    GlobalDeclaration.isPlaying
                )// Show notification

            }
            setOnCompletionListener {
                playNext()
            }
        }

        // Update current audio index
        //currentAudioIndex = audioList.indexOf(audio)

        // Update currently playing index in the adapter
        audioAdapter.setCurrentlyPlayingIndex(audioList.indexOf(audio))

        // Highlight the current song in the RecyclerView
        highlightCurrentSong()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    isUserSeeking = true
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler?.removeCallbacks(updateSeekBarRunnable)
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                handler?.postDelayed(updateSeekBarRunnable, 1000)
                isUserSeeking = false
            }
        })

        playPauseButton.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                playPauseButton.setImageResource(R.drawable.ic_play)
                NotificationHelper.showNotification(
                    requireContext(),
                    songTitleTextView.text.toString(),
                    audioList[currentAudioIndex].artist,
                    false
                )
            } else {
                mediaPlayer?.start()
                playPauseButton.setImageResource(R.drawable.ic_pause)
                NotificationHelper.showNotification(
                    requireContext(),
                    songTitleTextView.text.toString(),
                    audioList[currentAudioIndex].artist,
                    true
                )
            }
        }

        previousButton.setOnClickListener {
            playPrevious()
        }

        nextButton.setOnClickListener {
            playNext()
        }
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                // Stop playback if audio focus is permanently lost
                mediaPlayer?.release()
                mediaPlayer = null
                playPauseButton.setImageResource(R.drawable.ic_play)
                handler?.removeCallbacks(updateSeekBarRunnable)
                wasPlayingBeforeFocusLoss = false
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Pause playback temporarily
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                    playPauseButton.setImageResource(R.drawable.ic_play)
                    wasPlayingBeforeFocusLoss = true
                }
            }

            AudioManager.AUDIOFOCUS_GAIN -> {
                // Resume playback if it was playing before focus loss
                if (wasPlayingBeforeFocusLoss && isAppInBackground) {
                    mediaPlayer?.start()
                    playPauseButton.setImageResource(R.drawable.ic_pause)
                    wasPlayingBeforeFocusLoss = false
                }
            }
        }
    }

    private fun requestAudioFocus() {
        if (!audioFocusRequested) {
            val result = audioManager?.requestAudioFocus(
                this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN
            )
            audioFocusRequested = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    private fun abandonAudioFocus() {
        if (audioFocusRequested) {
            audioManager?.abandonAudioFocus(this)
            audioFocusRequested = false
        }
    }

    override fun onResume() {
        super.onResume()
        isAppInBackground = false
        requestAudioFocus()
    }

    override fun onPause() {
        super.onPause()
        isAppInBackground = true
        abandonAudioFocus()
    }

    // Define a callback class for MediaSessionCompat
    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onPlay() {
            // Handle play button press
            // Start or resume playback
            mediaPlayer?.start()
        }

        override fun onPause() {
            // Handle pause button press
            // Pause playback
            mediaPlayer?.pause()
        }

        override fun onStop() {
            // Handle stop button press
            // Stop playback
            mediaPlayer?.stop()
        }

        override fun onSkipToNext() {
            // Handle skip to next button press
            // Play the next track
            playNext()
        }

        override fun onSkipToPrevious() {
            // Handle skip to previous button press
            // Play the previous track

            playPrevious()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        handler?.removeCallbacks(updateSeekBarRunnable)
        abandonAudioFocus()
        mediaSession.release()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun highlightCurrentSong() {
        audioAdapter.notifyDataSetChanged()
    }

}