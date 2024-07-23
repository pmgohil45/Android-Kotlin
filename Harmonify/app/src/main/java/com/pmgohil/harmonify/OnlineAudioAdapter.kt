package com.pmgohil.harmonify

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnlineAudioAdapter(
    private var trackList: List<Track>, private val onItemClickListener: (Track) -> Unit
) : RecyclerView.Adapter<OnlineAudioAdapter.SongViewHolder>() {
    private var songs = mutableListOf<Track>()
    private var currentPlayingPosition: Int = -1

    fun setData(newSongs: List<Track>) {
        songs.clear()
        songs.addAll(newSongs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.online_item_track, parent, false)
        return SongViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int = songs.size

    class SongViewHolder(itemView: View, private val onItemClick: (Track) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val songTitle: TextView = itemView.findViewById(R.id.trackName)
        private val songArtist: TextView = itemView.findViewById(R.id.artistName)

        fun bind(track: Track) {
            songTitle.text = track.name
            songArtist.text = track.artists.joinToString(", ") { it.name }
            itemView.setOnClickListener { onItemClick(track) }
        }
    }

    // Method to set the currently playing index
    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentlyPlayingIndex(index: Int) {
        currentPlayingPosition = index
        notifyDataSetChanged()
    }

    // Method to get a random audio item
    fun getRandomAudio(): Track? {
        if (trackList.isNotEmpty()) {
            val randomIndex: Int = (0 until trackList.size).random()
            return trackList[randomIndex]
        }
        return null
    }
    /*
    private var songs = mutableListOf<Track>()
    private var currentPlayingPosition: Int = -1

    fun setData(newSongs: List<Track>) {
        songs.clear()
        songs.addAll(newSongs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.online_item_track, parent, false)
        return SongViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int = songs.size

    class SongViewHolder(itemView: View, private val onItemClick: (Track) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val songTitle: TextView = itemView.findViewById(R.id.trackName)
        private val songArtist: TextView = itemView.findViewById(R.id.artistName)

        fun bind(track: Track) {
            songTitle.text = track.name
            songArtist.text = track.artists.joinToString(", ") { it.name }
            itemView.setOnClickListener { onItemClick(track) }
        }
    }

    // Method to set the currently playing index
    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentlyPlayingIndex(index: Int) {
        currentPlayingPosition = index
        notifyDataSetChanged()
    }

    // Method to get a random audio item
    fun getRandomAudio(): Track? {
        if (trackList.isNotEmpty()) {
            val randomIndex: Int = (0 until trackList.size).random()
            return trackList[randomIndex]
        }
        return null
    }*/


}