package com.pmgohil.thoughtsadmin

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminUsersAdapter(
    private val context: Context,
    private val adminUserList: List<AdminUsersData>,
    private val onRemoveClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<AdminUsersAdapter.ViewHolder>() {

    private var item_count = 0
    private lateinit var popupWindow: PopupWindow


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_count: TextView = itemView.findViewById(R.id.txt_count)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val passwordTextView: TextView = itemView.findViewById(R.id.passwordTextView)
        val roleTextView: TextView = itemView.findViewById(R.id.roleTextView)
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        val removeTextView: TextView = itemView.findViewById(R.id.removeTextView)

        init {
            removeTextView.setOnClickListener {
                try {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onRemoveClickListener(position)
                    }
                } catch (e: Exception) {
                    GlobalVariableFunction.showToast(context, e.toString())
                }
            }
            roleTextView.setOnClickListener {
                showRolePopup(it, adminUserList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.admin_users_layout, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adminUserData = adminUserList[position]

        item_count = position + 1
        holder.txt_count.text = "{$item_count}"

        holder.emailTextView.text = "Email: " + adminUserData.email
        holder.passwordTextView.text = "Password: " + adminUserData.password
        holder.usernameTextView.text = "User Name: " + adminUserData.unm

        //set color
        val roleText = "Role: " + adminUserData.role
        val spannableString = SpannableString(roleText)
        val color = ContextCompat.getColor(context, R.color.dark_green)
        val startIndex = roleText.indexOf(adminUserData.role)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + adminUserData.role.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.roleTextView.text = spannableString

    }

    override fun getItemCount(): Int {
        return adminUserList.size
    }

    private fun showRolePopup(view: View, adminUserData: AdminUsersData) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.admin_role_popup_layout, null)

        val radioGroup = popupView.findViewById<RadioGroup>(R.id.radioGroup)
        val radioAdmin = popupView.findViewById<RadioButton>(R.id.radioAdmin)
        val radioEditor = popupView.findViewById<RadioButton>(R.id.radioEditor)
        val radioSuperAdmin = popupView.findViewById<RadioButton>(R.id.radioSuperAdmin)
        try {

            // Set the initial checked state based on the user's current role
            when (adminUserData.role) {
                "Admin" -> radioAdmin.isChecked = true
                "Editor" -> radioEditor.isChecked = true
                "Super Admin" -> radioSuperAdmin.isChecked = true
            }

            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            popupWindow.showAsDropDown(view)
            // Handle the selected role
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioAdmin -> {
                        // Handle Admin role selection
                        // You may want to update the user's role in the data and UI here
                        updateRoleAndDismiss(adminUserData.unm, "Admin")
                    }

                    R.id.radioEditor -> {
                        // Handle Editor role selection
                        updateRoleAndDismiss(adminUserData.unm, "Editor")
                    }

                    R.id.radioSuperAdmin -> {
                        // Handle Super Admin role selection
                        updateRoleAndDismiss(adminUserData.unm, "Super Admin")
                    }
                }
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(context, e.toString())
        }
    }

    private fun updateRoleAndDismiss(username: String, newRole: String) {
        // Update the user's role in the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val adminUsersRef = database.getReference("admin_user")
        try {

            // Find the user by username and update the role
            adminUsersRef.orderByChild("unm").equalTo(username)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (userSnapshot in snapshot.children) {
                            userSnapshot.ref.child("role").setValue(newRole)
                        }

                        // Dismiss the popup window
                        popupWindow.dismiss()

                        // Notify the adapter that the data has changed
                        notifyDataSetChanged()
                    }

                    override fun onCancelled(e: DatabaseError) {
                        GlobalVariableFunction.showToast(context, e.toString())
                    }
                })
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(context, e.toString())
        }
    }
}

