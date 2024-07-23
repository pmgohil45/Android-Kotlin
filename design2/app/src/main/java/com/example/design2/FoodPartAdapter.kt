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

class FoodPartAdapter(
    private val context: Context,
    private val mList: ArrayList<FoodModel>,
    private var toast: Toast? = null
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_part_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        Glide.with(context)
            .load(ItemsViewModel.foodImg)
            .into(holder.imageView)

        holder.foodPrice.text = ItemsViewModel.foodName
        holder.foodName.setOnClickListener() {
            cancelToast()
            toast = Toast.makeText(context, ItemsViewModel.foodName, Toast.LENGTH_SHORT)
            toast!!.show()
        }

        holder.foodName.text = ItemsViewModel.foodPrice.toString()
        holder.foodPrice.setOnClickListener() {
            Toast.makeText(context, ItemsViewModel.foodPrice.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val foodName: TextView = itemView.findViewById(R.id.foodName)
    val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
}

private fun cancelToast() {
   /* if (toast != null) {
        toast?.cancelToast()
    }*/
}