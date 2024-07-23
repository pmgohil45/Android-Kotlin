package com.pmgohil.harmonify

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AudioAdapter(
    private val context: Context,
    private var audioList: List<AudioDataClass>,
    private var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(audio: AudioDataClass)
    }

    private var currentlyPlayingIndex: Int = -1

    class ViewHolder(view: View, private val itemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        val titleTextView: TextView = view.findViewById(R.id.title)
        val artistTextView: TextView = view.findViewById(R.id.artist)
        private lateinit var audio: AudioDataClass

        init {
            view.setOnClickListener(this)
        }

        fun bind(audio: AudioDataClass, isPlaying: Boolean) {
            this.audio = audio
            titleTextView.text = audio.title
            artistTextView.text = audio.artist

            // Set text color based on playback state
            val titleColorRes =
                if (isPlaying) {
                    R.color.highlighted_music_color
                } else {
                    R.color.text_color
                }
            val artistColorRes =
                if (isPlaying) {
                    R.color.highlighted_music_color
                } else {
                    R.color.text_color
                }

            titleTextView.setTextColor(ContextCompat.getColor(itemView.context, titleColorRes))
            artistTextView.setTextColor(ContextCompat.getColor(itemView.context, artistColorRes))
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(audio)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.audio_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val audio = audioList[position]
        val isPlaying = position == currentlyPlayingIndex
        holder.bind(audio, isPlaying)

        // Set click listener
        holder.itemView.setOnClickListener {
            if (isPlaying) {
                // Show toast if the currently playing song is clicked
                //Toast.makeText(context, "This song is already playing", Toast.LENGTH_SHORT).show()
            } else {
                // Notify the listener of the item click
                itemClickListener.onItemClick(audio)
                // Update the currently playing index
                updateCurrentlyPlayingIndex(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    // Method to update the list of audio items
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<AudioDataClass>) {
        audioList = newList
        notifyDataSetChanged()
    }

    // Method to update the currently playing index and refresh the list
    private fun updateCurrentlyPlayingIndex(newIndex: Int) {
        val previousIndex = currentlyPlayingIndex
        currentlyPlayingIndex = newIndex
        notifyItemChanged(previousIndex)
        notifyItemChanged(currentlyPlayingIndex)
    }

    // Method to get a random audio item
    fun getRandomAudio(): AudioDataClass? {
        if (audioList.isNotEmpty()) {
            val randomIndex = (0 until audioList.size).random()
            return audioList[randomIndex]
        }
        return null
    }

    // Method to set the currently playing index
    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentlyPlayingIndex(index: Int) {
        currentlyPlayingIndex = index
        notifyDataSetChanged()
    }
}