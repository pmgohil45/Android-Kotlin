package com.example.apirecyclerview

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class customAdapter(
    private val context: Activity,
    private val mList: List<ViewModel>
) : RecyclerView.Adapter<customAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_adapter, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        //    holder.textView1.text = ItemsViewModel.id.toString()
        holder.textView2.text = ItemsViewModel.email
        holder.textView3.text = ItemsViewModel.fnm
        holder.textView4.text = ItemsViewModel.lnm

        var avatar = ItemsViewModel.avatar

        if (avatar !== null) {
            Glide.with(context)
                .load(avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_background)
        }
//        // for the color redius
//        val rnd = Random()
//        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
//        holder.llRoot.backgroundTintList = ColorStateList.valueOf(color)
//
//        val rnd1 = Random()
//        val color1 = Color.argb(255, rnd1.nextInt(256), rnd1.nextInt(256), rnd1.nextInt(256))
//    //    holder.textView1.setTextColor(ColorStateList.valueOf(color1))
//
//        val color2 = Color.argb(255, rnd1.nextInt(256), rnd1.nextInt(256), rnd1.nextInt(256))
//        holder.textView2.setTextColor(ColorStateList.valueOf(color2))
//
//        val color3 = Color.argb(255, rnd1.nextInt(256), rnd1.nextInt(256), rnd1.nextInt(256))
//        holder.textView3.setTextColor(ColorStateList.valueOf(color3))
//
//        val color4 = Color.argb(255, rnd1.nextInt(256), rnd1.nextInt(256), rnd1.nextInt(256))
//        holder.textView4.setTextColor(ColorStateList.valueOf(color4))
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //val textView1: TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val textView3: TextView = itemView.findViewById(R.id.textView3)
        val textView4: TextView = itemView.findViewById(R.id.textView4)
        val imageView: AppCompatImageView = itemView.findViewById(R.id.imageView)
        //val llRoot: LinearLayout = itemView.findViewById(R.id.llRoot)
    }
}