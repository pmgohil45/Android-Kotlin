package com.pmgohil.thoughtsadmin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import org.checkerframework.checker.index.qual.GTENegativeOne

class SelectThoughtsAdapter(
    private val context: Context,
    private var thoughtsList: List<ThoughtsData>,
    private val databaseReference: DatabaseReference
) : RecyclerView.Adapter<SelectThoughtsAdapter.ViewHolder>() {
    private var item_count = 0

    /*
        fun setThoughtsList(thoughtsData: List<ThoughtsData>) {
            thoughtsList = thoughtsData
            notifyDataSetChanged()
        }
    */

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SelectThoughtsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.select_thoughts_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectThoughtsAdapter.ViewHolder, position: Int) {
        val thoughtsData = thoughtsList[position]
        //its gives count
        item_count = position + 1
        holder.txt_count.text = "{$item_count}"
        //its print thoughts
        holder.txt_thoughts.text = thoughtsData.thoughts

        holder.removeImageView.setOnClickListener {
            val thoughtKey = thoughtsData.thoughts_key
            thoughtKey?.let { key ->
                // Assuming your databaseReference is initialized properly
                val thoughtRef = databaseReference.child(key)

                // Remove the item from the database
                thoughtRef.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        GlobalVariableFunction.showToast(context, "Successfully Delete!")
                    } else {
                        GlobalVariableFunction.showToast(context, "Error!")
                    }
                }
            }
        }
        holder.editImageView.setOnClickListener {
            holder.txt_count.visibility = View.GONE
            holder.txt_thoughts.visibility = View.GONE
            holder.removeImageView.visibility = View.GONE
            holder.editImageView.visibility = View.GONE
            holder.et_thoughts.visibility = View.VISIBLE
            holder.updateImageView.visibility = View.VISIBLE
            holder.et_thoughts.setText(holder.txt_thoughts.text.toString())

            GlobalVariableFunction.showToast(context, thoughtsData.thoughts_key.toString())

            holder.updateImageView.setOnClickListener {
                holder.txt_count.visibility = View.VISIBLE
                holder.txt_thoughts.visibility = View.VISIBLE
                holder.removeImageView.visibility = View.VISIBLE
                holder.editImageView.visibility = View.VISIBLE
                holder.et_thoughts.visibility = View.GONE
                holder.updateImageView.visibility = View.GONE

                val thoughtKey = thoughtsData.thoughts_key
                thoughtKey?.let { key ->
                    val thoughtRef = databaseReference.child(key)
                    val updatedText = holder.et_thoughts.text.toString()

                    // Update the text in the database
                    thoughtRef.child("thoughts").setValue(updatedText)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                // Item updated successfully
                            } else {
                                // Handle the error
                            }
                        }

                    val edit_thoughts_key = thoughtsData.thoughts_key.substringBefore("-").trim()
                    val new_thoughts_key = "$edit_thoughts_key - $updatedText"
                    thoughtRef.child("thoughts_key").setValue(new_thoughts_key)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                // Item updated successfully
                            } else {
                                // Handle the error
                            }
                        }
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return thoughtsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_count: TextView = itemView.findViewById(R.id.txt_count)
        val txt_thoughts: TextView = itemView.findViewById(R.id.txt_thoughts)
        val removeImageView: ImageView = itemView.findViewById(R.id.removeImageView)
        val editImageView: ImageView = itemView.findViewById(R.id.editImageView)
        val et_thoughts: EditText = itemView.findViewById(R.id.et_thoughts)
        val updateImageView: ImageView = itemView.findViewById(R.id.updateImageView)


        /*        init {
                    removeImageView.setOnClickListener {
                        try {
                            onItemDeleteListener.onDeleteItemClick(thoughtsData.thoughts_key)
                            *//*                    val position = adapterPosition
                                        if (position != RecyclerView.NO_POSITION) {
                                            onRemoveClickListener(position)
                                        }*//*
                } catch (e: Exception) {
                    //GlobalVariableFunction.showToast(context, e.toString())
                }
            }
        }*/
    }

}