package com.example.project9

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.project9.view_modal.data_modal

class CustomAdapter(private val context: Context, private val list: ArrayList<data_modal>):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_hold = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_adapter, parent, false)

        return ViewHolder(view_hold)
    }

    // used to perform any item from xml code
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemViewModel = list[position]
        holder.text_view.text = itemViewModel.a1 + " - " + itemViewModel.a2
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        val text_view = item_view.findViewById<Button>(R.id.button)
    }
}