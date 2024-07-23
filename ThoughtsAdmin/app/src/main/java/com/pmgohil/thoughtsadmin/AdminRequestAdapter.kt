package com.pmgohil.thoughtsadmin

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class AdminRequestAdapter(
    private val context: Context,
    private val adminRequestList: List<AdminRequestData>,
    private val onRemoveClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<AdminRequestAdapter.ViewHolder>() {
    private var item_count = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_count_admin_request: TextView = itemView.findViewById(R.id.txt_count_admin_request)
        val txt_user_name_admin_request: TextView =
            itemView.findViewById(R.id.txt_user_name_admin_request)
        val txt_email_admin_request: TextView = itemView.findViewById(R.id.txt_email_admin_request)
        val txt_password_admin_request: TextView =
            itemView.findViewById(R.id.txt_password_admin_request)
        val txt_remove_admin_request: TextView =
            itemView.findViewById(R.id.txt_remove_admin_request)
        val txt_approved_admin_request: TextView =
            itemView.findViewById(R.id.txt_approved_admin_request)

        init {
            txt_remove_admin_request.setOnClickListener {
                try {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onRemoveClickListener(position)
                    }
                } catch (e: Exception) {
                    //GlobalVariableFunction.showToast(context, e.toString())
                }
            }

            txt_approved_admin_request.setOnClickListener {
                try {
                    handleAdminApprovalRequest(adminRequestList[position])
                } catch (e: Exception) {
                    GlobalVariableFunction.showToast(context, e.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_request_layout, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adminRequestData = adminRequestList[position]

        item_count = position + 1
        holder.txt_count_admin_request.text = "{$item_count}"

        holder.txt_user_name_admin_request.text = "User Name: ${adminRequestData.unm}"
        holder.txt_email_admin_request.text = "Email: ${adminRequestData.email}"
        holder.txt_password_admin_request.text = "Password: ${adminRequestData.password}"

    }

    override fun getItemCount(): Int {
        return adminRequestList.size
    }

    private fun handleAdminApprovalRequest(adminRequestData: AdminRequestData) {
        val database = FirebaseDatabase.getInstance()

        // Get selected data from admin_request
        val email = adminRequestData.email
        val password = adminRequestData.password
        val unm = adminRequestData.unm

        // Insert data into admin_user
        val adminUsersRef = database.getReference("admin_user")
        val newAdminUserRef = adminUsersRef.child(unm)
        newAdminUserRef.setValue(
            mapOf(
                "email" to email,
                "password" to password,
                "role" to "Editor",
                "unm" to unm
            )
        )
            .addOnSuccessListener {
                // Data successfully inserted into admin_user

                // Delete data from admin_request
                val adminRequestRef = database.getReference("admin_request").child(unm)
                adminRequestRef.removeValue()
                    .addOnSuccessListener {
                        // Data successfully deleted from admin_request

                        // Notify the adapter that the data has changed
                        notifyDataSetChanged()

                        /*GlobalVariableFunction.showToast(
                            context,
                            "Admin approval request handled successfully."
                        )*/
                    }
                    .addOnFailureListener { e ->
                        // Failed to delete data from admin_request
                        GlobalVariableFunction.showToast(
                            context,
                            "Failed to delete admin approval request: ${e.message}"
                        )
                    }
            }
            .addOnFailureListener { e ->
                // Failed to insert data into admin_user
                GlobalVariableFunction.showToast(
                    context,
                    "Failed to insert admin approval request: ${e.message}"
                )
            }
    }
}