package com.example.design2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodRefer(
    private val context: Context,
    private val mList: ArrayList<ReferEarnModel>,
    private var toast: Toast? = null
) : RecyclerView.Adapter<FoodRefer.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_refer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        Glide.with(context)
            .load(ItemsViewModel.contactImg)
            .into(holder.imageView)

        holder.refer.text = ItemsViewModel.referDescription.toString()
        holder.refer.setOnClickListener() {
            cancelToast()
            toast = Toast.makeText(context, ItemsViewModel.referAndEarn, Toast.LENGTH_SHORT)
            toast!!.show()
        }
        holder.descRefer.text = ItemsViewModel.referAndEarn
        holder.descRefer.setOnClickListener() {
            Toast.makeText(context, ItemsViewModel.referDescription.toString(), Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.contactImg)
        val refer: TextView = itemView.findViewById(R.id.refer)
        val descRefer: TextView = itemView.findViewById(R.id.descRefer)
    }

    private fun cancelToast() {
        /* if (toast != null) {
             toast?.cancelToast()
         }*/
    }
}