package com.example.design2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodCategoryAdapter(
    private val context: Context,
    private val mList: Array<String>
    //private val mList: ArrayList<viewModel>
) : RecyclerView.Adapter<FoodCategoryAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_cataefory_adapter, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.txt.text = ItemsViewModel

        // sets the text to the textview from our itemHolder class
        //    holder.textView1.text = ItemsViewModel.id.toString()
//        holder.img1.text = ItemsViewModel.email
//        holder.img2.text = ItemsViewModel.fnm
//        holder.img3.text = ItemsViewModel.lnm

        //var img = ItemsViewModel.img
/*
        if (img !== null) {
            Glide.with(context)
                .load(img)
                //.apply(RequestOptions.circleCropTransform())
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_background)
        }*/
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

          val txt: TextView = itemView.findViewById(R.id.txt)
    }
}