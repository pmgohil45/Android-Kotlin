package com.pmgohil.thoughtsadmin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SelectImageAdapter(
    private val imageList: List<ImageModel>,
    private val onRemoveClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<SelectImageAdapter.ViewHolder>() {

    private var item_count = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_count: TextView = itemView.findViewById(R.id.txt_count)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val removeImageView: ImageView = itemView.findViewById(R.id.removeImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.select_image_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageModel = imageList[position]
        item_count = position + 1
        holder.txt_count.text = "{$item_count}"

        Glide.with(holder.itemView).load(imageModel.imageUrl).into(holder.imageView)

        holder.removeImageView.setOnClickListener {
            onRemoveClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}