package com.pmgohil.thoughtsadmin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.auth.User

class UserAdapter(
    private val context: Context,
    private val userList: List<UserData>,
    private val onRemoveClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var item_count = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_count: TextView = itemView.findViewById(R.id.txt_count)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val userImageView: ImageView = itemView.findViewById(R.id.userImageView)
        val removeImageView: ImageView = itemView.findViewById(R.id.removeImageView)

        init {
            removeImageView.setOnClickListener {
                try {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onRemoveClickListener(position)
                    }
                } catch (e: Exception) {
                    //GlobalVariableFunction.showToast(context, e.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = userList[position]

        item_count = position + 1
        holder.txt_count.text = "{$item_count}"

        holder.nameTextView.text = userData.name
        holder.emailTextView.text = userData.email

        // Load user image using Glide
        Glide.with(holder.itemView.context)
            .load(userData.mail_id_image) // Use the appropriate field for the user image
            .placeholder(R.drawable.baseline_add_reaction_24).into(holder.userImageView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}