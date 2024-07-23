package com.pmgohil.harmonify

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import okhttp3.OkHttpClient

class OnlineAudioFragment : Fragment() {
    //, AudioManager.OnAudioFocusChangeListener {
    companion object {
        private const val ARG_ACCESS_TOKEN = "access_token"
        private val clientId = "8080330159e34a65ba6eee8da2677757"   // Replace with your client ID
        private val redirectUri = "https://pmgohil.site/"        // Replace with your redirect URI
        private val REQUEST_CODE = 1337

        fun newInstance(token: String): OnlineAudioFragment {
            val fragment = OnlineAudioFragment()
            val args = Bundle()
            args.putString(ARG_ACCESS_TOKEN, token)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var search_bar_online: EditText
    private lateinit var recommendations_list_online: RecyclerView
    private lateinit var onlineAudioAdapter: OnlineAudioAdapter
    private lateinit var playPauseButtonOnline: ImageView
    private lateinit var previousButtonOnline: ImageView
    private lateinit var nextButtonOnline: ImageView
    private lateinit var songTitleOnline: TextView
    private lateinit var albumArtworkOnline: ImageView
    private lateinit var playbackControlsOnline: CardView
    private lateinit var shuffleToggleButtonOnline: ToggleButton
    private lateinit var seekBarOnline: SeekBar
    private lateinit var countdownTimerTextViewOnline: TextView
    private lateinit var totalTimerTextViewOnline: TextView

    private var isPlaying = false
    private var currentSongUrl: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private var accessToken: String? = null
    private var currentPlayingPosition: Int = -1
    private var trackList: List<Track> = listOf()
    private var handler: Handler? = null
    private var audioManager: AudioManager? = null
    private var audioFocusRequested: Boolean = false
    private var isShuffleEnabled: Boolean = false


    // private val client = OkHttpClient()
    /*
    private val clientId = "8080330159e34a65ba6eee8da2677757"
    private val clientSecret = "3ad90255caf948cbb583df47bf05c953"
    private val spotifyBaseUrl = "https://api.spotify.com/"
    private lateinit var spotifyService: SpotifyService
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accessToken = it.getString(ARG_ACCESS_TOKEN).orEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_online_audio, container, false)
        playPauseButtonOnline = view.findViewById(R.id.playPauseButtonOnline)
        previousButtonOnline = view.findViewById(R.id.previousButtonOnline)
        nextButtonOnline = view.findViewById(R.id.nextButtonOnline)
        songTitleOnline = view.findViewById(R.id.songTitleOnline)
        albumArtworkOnline = view.findViewById(R.id.albumArtworkOnline)
        playbackControlsOnline = view.findViewById(R.id.playbackControlsOnline)
        recommendations_list_online = view.findViewById(R.id.recommendations_list_online)
        search_bar_online = view.findViewById(R.id.search_bar_online)
        shuffleToggleButtonOnline = view.findViewById(R.id.shuffleToggleButtonOnline)
        seekBarOnline = view.findViewById(R.id.seekBarOnline)
        countdownTimerTextViewOnline = view.findViewById(R.id.countdownTimerTextViewOnline)
        totalTimerTextViewOnline = view.findViewById(R.id.totalTimerTextViewOnline)

        recommendations_list_online.layoutManager = LinearLayoutManager(requireContext())
        onlineAudioAdapter = OnlineAudioAdapter(trackList) { track -> playSong(track) }

        recommendations_list_online.adapter = onlineAudioAdapter

        recommendations_list_online.layoutManager = LinearLayoutManager(context)
        onlineAudioAdapter = OnlineAudioAdapter(trackList) { track -> playSong(track) }
        recommendations_list_online.adapter = onlineAudioAdapter
        //spotifyAuthRequest()
        return view
//        return inflater.inflate(R.layout.fragment_online_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        getIntent = activity?.intent!!
        // spotifyAuthRequest()

    }

    // Spotify Authorization Request
    /*
        private fun spotifyAuthRequest() {
            val request =
                AuthorizationRequest.Builder(clientId, AuthorizationResponse.Type.TOKEN, redirectUri)
                    .setScopes(arrayOf("user-library-read")).build()

            AuthorizationClient.openLoginActivity(requireActivity(), REQUEST_CODE, request)
            Log.e("H45", " Request: $request")
            Log.e("H45", "Request Code: ${GlobalDeclaration.requestCode}")
            Log.e("H45", "Result Code: ${GlobalDeclaration.resultCode}")
            Log.e("H45", "Intent Data: ${GlobalDeclaration.data}")

            handleActivityResult(
                GlobalDeclaration.requestCode,
                GlobalDeclaration.resultCode,
                GlobalDeclaration.data
            )
        }
    */

    /*
                override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                    super.onActivityResult(requestCode, resultCode, data)

                            if (requestCode == REQUEST_CODE) {
                                val response = AuthorizationClient.getResponse(resultCode, data)
                                when (response.type) {
                                    AuthorizationResponse.Type.TOKEN -> {
                                        accessToken = response.accessToken
                                        fetchSongsFromSpotifyAPI()
                                    }

                                    AuthorizationResponse.Type.ERROR -> {
                                        Toast.makeText(
                                            requireContext(), "Login Error: ${response.error}", Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    else -> {
                                        Toast.makeText(requireContext(), "Please Login", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "$REQUEST_CODE", Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(), "$requestCode", Toast.LENGTH_SHORT).show()
                            }
                }
            */

    /*
        fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == REQUEST_CODE) {
                if (data != null) {
                    val response = AuthorizationClient.getResponse(resultCode, data)
                    Log.e("h45", "$response")
                    when (response.type) {

                        AuthorizationResponse.Type.TOKEN -> {
                            accessToken = response.accessToken
                            fetchSongsFromSpotifyAPI()
                        }

                        AuthorizationResponse.Type.ERROR -> {
                            Toast.makeText(
                                requireContext(), "Login Error: ${response.error}", Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            Toast.makeText(requireContext(), "Please Login", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "$REQUEST_CODE", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), "$requestCode", Toast.LENGTH_SHORT).show()
            }
        }
    */

    private fun fetchSongsFromSpotifyAPI() {
        if (accessToken == null) {
            Toast.makeText(context, "Access token is null", Toast.LENGTH_SHORT).show()
            return
        }

        val retrofit = Retrofit.Builder().baseUrl("https://api.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(SpotifyService::class.java)
        val call = service.getUserTracks("Bearer $accessToken")

        call.enqueue(object : Callback<PagingObject<SavedTrack>> {
            override fun onResponse(
                call: Call<PagingObject<SavedTrack>>, response: Response<PagingObject<SavedTrack>>
            ) {
                if (response.isSuccessful) {
                    val tracks = response.body()?.items?.map {
                        it.track // Use the Track object directly
                    } ?: emptyList()
                    onlineAudioAdapter.setData(tracks)
                } else {
                    Log.e("H45", "Error fetching songs: ${response.message()}")
                    Toast.makeText(
                        context, "Error fetching songs: ${response.message()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PagingObject<SavedTrack>>, t: Throwable) {
                Log.e("H45", "Error fetching songs", t)
                Toast.makeText(context, "Error fetching songs", Toast.LENGTH_SHORT).show()
            }
        })

        /*        Log.e("H45", "Access Token: $accessToken")
        Toast.makeText(requireContext(), "$accessToken", Toast.LENGTH_SHORT).show()

        // Use the access token to make API requests

        setupPlaybackControls()
        searchTracks("latest")

        view?.findViewById<EditText>(R.id.search_bar_online)
            ?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    searchTracks(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        view?.findViewById<ToggleButton>(R.id.shuffleToggleButtonOnline)
            ?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Shuffle is enabled
                    playRandomSong()
                } else {
                    // Shuffle is disabled
                    playNext()
                }
            }

        view?.findViewById<TextView>(R.id.songTitleOnline)?.isSelected = true*/
    }

    private fun setupPlaybackControls() {
        playPauseButtonOnline.setOnClickListener { togglePlayPause() }
        nextButtonOnline.setOnClickListener { playNext() }
        previousButtonOnline.setOnClickListener { playPrevious() }
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        //releasePlayer()
    }*/

    /*fun onItemClick(track: Track) {
        playSong(track)
        //playTrack(track)
    }*/

    private fun searchTracks(query: String) {
        val token = "Bearer $accessToken"
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.searchTracks(token, query)
                }
                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    searchResponse?.tracks?.items?.let { tracks ->
                        onlineAudioAdapter.setData(tracks)
                        trackList = tracks
                    } ?: run {
                        Log.e("H45", "Response body is null or empty")
                    }
                } else {
                    Log.e("H45", "API call failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("H45", "Error: $e")
            }
        }
    }

    /*    private fun fetchAlbums() {
        val accessToken = "Bearer " + (activity as MainActivity).accessToken
        Log.e("H45", "Access Token: $accessToken")
        lifecycleScope.launch {
            try {
                val userResponse = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getCurrentUser(accessToken)
                }
                if (userResponse.isSuccessful) {
                    val userProfile = userResponse.body()
                    Log.e("H45", "Access token is valid. User ID: ${userProfile?.id}")
                    val response: Response<PagingObject<SavedTrack>> = withContext(Dispatchers.IO) {
                        RetrofitInstance.api.getSavedTracks(accessToken, limit = 50, offset = 0)
                    }
                    if (response.isSuccessful) {
                        response.body()?.items?.let { items ->
                            try {
                                val tracks = items.map { it.track }
                                onlineAudioAdapter.setData(tracks)
                            } catch (e: Exception) {
                                Log.e("H45", "Error: $e")
                            }
                        } ?: run {
                            Log.e("H45", "Response body is null")
                        }
                    } else {
                        Log.e("H45", "Response not successful: ${response.errorBody()?.string()}")
                    }
                } else {
                    Log.e(
                        "H45",
                        "Access token is invalid or expired: ${userResponse.errorBody()?.string()}"
                    )


                }
            } catch (e: Exception) {
                Log.e("H45", "Error: $e")
            }
        }
    }*/

    /*private fun onSongItemClick(position: Int) {
        currentPlayingPosition = position
        playSong(trackList[position])
    }*/


    private fun playSong(song: Track) {

        currentPlayingPosition = trackList.indexOf(song)
        songTitleOnline.text = song.name
        // Glide.with(this).load(song.album.images.firstOrNull()?.url).into(albumArtworkOnline)

        playbackControlsOnline.visibility = View.VISIBLE
        currentSongUrl = song.preview_url

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(currentSongUrl)
            prepare()
            start()
            setOnPreparedListener {
                startPlayback(it)
            }
            setOnCompletionListener {
                playNext()
            }
        }
        isPlaying = true
        playPauseButtonOnline.setImageResource(R.drawable.ic_pause)
    }

    private fun startPlayback(player: MediaPlayer) {
        player.start()
        seekBarOnline.max = player.duration
        seekBarOnline.progress = player.currentPosition
        handler = Handler()
        handler?.postDelayed(updateSeekBarRunnable, 1000)
        updateTimer(player.currentPosition)
        playPauseButtonOnline.setImageResource(R.drawable.ic_pause)

        // Request audio focus when playback starts
        //requestAudioFocus()

        // Show notification
        NotificationHelper.showNotification(
            requireContext(),
            songTitleOnline.text.toString(),
            trackList[currentPlayingPosition].artists.toString(),
            true
        )

    }

    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                val currentPosition = it.currentPosition
                seekBarOnline.progress = currentPosition
                handler?.postDelayed(this, 1000)
                updateTimer(currentPosition)
            }
        }
    }

/*
    private fun requestAudioFocus() {
        if (!audioFocusRequested) {
            val result = audioManager?.requestAudioFocus(
                this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN
            )
            audioFocusRequested = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }
*/

    private fun updateTimer(currentDuration: Int) {
        val remainingTime = mediaPlayer?.duration?.minus(currentDuration) ?: 0
        val totalMinutes = remainingTime / 1000 / 60
        val totalSeconds = remainingTime / 1000 % 60
        val currentMinutes = currentDuration / 1000 / 60
        val currentSeconds = currentDuration / 1000 % 60

        countdownTimerTextViewOnline.text = String.format("%02d:%02d", totalMinutes, totalSeconds)
        totalTimerTextViewOnline.text = String.format("%02d:%02d", currentMinutes, currentSeconds)
    }

    private fun togglePlayPause() {
        mediaPlayer?.let {
            if (isPlaying) {
                it.pause()
                playPauseButtonOnline.setImageResource(R.drawable.ic_play)
            } else {
                it.start()
                playPauseButtonOnline.setImageResource(R.drawable.ic_pause)
            }
            isPlaying = !isPlaying
        }
    }

    private fun playNext() {
        if (shuffleToggleButtonOnline.isChecked != isShuffleEnabled) {
            playRandomSong()
        } else {
            if (currentPlayingPosition < trackList.size - 1) {
                currentPlayingPosition++
                playSong(trackList[currentPlayingPosition])
                onlineAudioAdapter.setCurrentlyPlayingIndex(currentPlayingPosition)
            }
        }
    }

    private fun playRandomSong() {
//        changeButtonColorTemporarily()
        val randomAudio = onlineAudioAdapter.getRandomAudio()
        if (randomAudio != null) {
            //setupMediaPlayer(randomAudio)
            playSong(randomAudio)
        }

        onlineAudioAdapter.setCurrentlyPlayingIndex(currentPlayingPosition)
        // Highlight the current song
        highlightCurrentSong()
    }

    private fun playPrevious() {
        if (currentPlayingPosition > 0) {
            playSong(trackList[--currentPlayingPosition])
        }
    }

/*    override fun onAudioFocusChange(p0: Int) {
        TODO("Not yet implemented")
    }*/

    private fun highlightCurrentSong() {
        onlineAudioAdapter.notifyDataSetChanged()
    }

    /*    private fun playTrack(track: Track) {
            releasePlayer()

            exoPlayer = SimpleExoPlayer.Builder(requireContext()).build()

            val dataSourceFactory = DefaultDataSourceFactory(
                requireContext(), Util.getUserAgent(requireContext(), "YourApplicationName")
            )
            val mediaItem = MediaItem.fromUri(Uri.parse(track.uri))
            val mediaSource =
                ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

            exoPlayer?.prepare(mediaSource)
            exoPlayer?.playWhenReady = true // Start playback immediately

            // Optional: Hook ExoPlayer to PlayerView for UI components
            val playerView = view?.findViewById<PlayerView>(R.id.playerView)
            playerView?.player = exoPlayer
        }

        private fun releasePlayer() {
            exoPlayer?.release()
            exoPlayer = null
        }*/

}
