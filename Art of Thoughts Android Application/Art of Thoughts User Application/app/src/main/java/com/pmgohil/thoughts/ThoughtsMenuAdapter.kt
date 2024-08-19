package com.pmgohil.thoughts

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class ThoughtsMenuAdapter(
    private val context: Context,
    private val menuItemList: List<MenuItem>,
    private val callback: MyThoughtsMenuAdapterCallback,
) : RecyclerView.Adapter<ThoughtsMenuAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewThoughtsMenu: CardView = itemView.findViewById(R.id.cardViewThoughtsMenu)
        val txtThoughtsMenu: TextView = itemView.findViewById(R.id.txtThoughtsMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.thoughts_menu_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItemList = menuItemList[position]

        holder.txtThoughtsMenu.text = menuItemList.title
        holder.cardViewThoughtsMenu.setOnClickListener {
            //Callback into the activity
            callback.onButtonClick(menuItemList.id)

            // Add a ripple effect to the itemLayout
            val rippleDrawable = RippleDrawable(
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.classic_rose)),
                holder.cardViewThoughtsMenu.background,
                null
            )
            ViewCompat.setBackground(holder.cardViewThoughtsMenu, rippleDrawable)

        }

    }

    override fun getItemCount(): Int {
        return menuItemList.size
    }
}