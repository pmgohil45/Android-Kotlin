package com.pmgohil.harmonify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GaanaOnlineAudioAdapter(private val songTrackList: List<SongTrack>) :
    RecyclerView.Adapter<GaanaOnlineAudioAdapter.TrackViewHolder>() {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trackTitle: TextView = itemView.findViewById(R.id.trackName)
        val trackArtist: TextView = itemView.findViewById(R.id.artistName)
        val trackImage: ImageView = itemView.findViewById(R.id.trackImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.online_item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val songTrack = songTrackList[position]
        holder.trackTitle.text = songTrack.trackTitle
        holder.trackArtist.text = songTrack.trackArtist
        Glide.with(holder.itemView.context).load(songTrack.trackImage).into(holder.trackImage)
    }

    override fun getItemCount(): Int = songTrackList.size
}
