package com.example.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class adapterClass1 (private val context: Context, private val arrList: Array<String>) :
    RecyclerView.Adapter<adapterClass1.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterClass1.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.design, parent, false)

        return ViewHolder(view)
    }

    // used to perform any item from xml code
    override fun onBindViewHolder(holder: adapterClass1.ViewHolder, position: Int) {
        val item_view_model = arrList[position]
        holder.textView.text = item_view_model
        holder.textView.setOnClickListener() {
            Toast.makeText(context, item_view_model, Toast.LENGTH_SHORT).show()
        }

    }

    // used to call any item from xml code
    class ViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        val textView: TextView = item_view.findViewById(R.id.textView)
        val button: Button = item_view.findViewById(R.id.button)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }
}