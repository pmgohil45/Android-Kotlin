package com.example.project8

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project8.R
import com.example.project8.edit_activity

class adapterClass(
    private val context: Context,
    private val mList: Array<String>
) :
    RecyclerView.Adapter<adapterClass.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_design, parent, false)

        return ViewHolder(view)
    }

    // used to perform any item from xml code
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.textView.text = ItemsViewModel

        holder.textView.setOnClickListener()
        {
            Toast.makeText(context, ItemsViewModel, Toast.LENGTH_SHORT).show()
        }
//        holder.textView.setOnClickListener() {
//            val intent1 = Intent(this@adapterClass, edit_activity::class.java)
//            startActivity(intent)
//        }
    }

    // used for call any item from xml code
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // itemView one kind of var
        val textView: TextView = itemView.findViewById(R.id.text)
        val edit_btn: Button = itemView.findViewById(R.id.editBtn)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}