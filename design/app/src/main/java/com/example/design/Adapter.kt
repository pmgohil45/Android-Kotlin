package layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.design.R

class adapter(private val context: Context, private val arrList: ArrayList<String>) :
    RecyclerView.Adapter<adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter, parent, false)

        return ViewHolder(view)
    }

    // used to perform any item from xml code
    override fun onBindViewHolder(holder: adapter.ViewHolder, position: Int) {
        val item_view_model = arrList[position]
        holder.apple.text = item_view_model
        holder.mengo.text = item_view_model
        holder.bnana.text = item_view_model
    }

    // used to call any item from xml code
    class ViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        val apple: Button = item_view.findViewById(R.id.apple)
        val mengo: Button = item_view.findViewById(R.id.mengo)
        val bnana: Button = item_view.findViewById(R.id.bnana)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }
}