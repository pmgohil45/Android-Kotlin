package com.pmgohil.thoughtsadmin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminRequestFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var adminRequestAdapter: AdminRequestAdapter
    private lateinit var adminRequestDataList: MutableList<AdminRequestData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_request, container, false)

        database = FirebaseDatabase.getInstance().reference.child("admin_request")
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adminRequestDataList = mutableListOf()
        adminRequestAdapter =
            AdminRequestAdapter(requireContext(), adminRequestDataList) { position ->
                deleteUser(position)
            }
        recyclerView.adapter = adminRequestAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchUsers()

        return view
    }

    private fun deleteUser(position: Int) {
        try {
            val admin_user_request = adminRequestDataList[position]
            val userReference = database.child(admin_user_request.unm)
            userReference.removeValue().addOnSuccessListener {
                try {
                    adminRequestDataList.removeAt(position)
                } catch (e: Exception) {
                    //GlobalVariableFunction.showToast(requireContext(), e.toString())
                }
            }.addOnFailureListener { error ->
                GlobalVariableFunction.showToast(requireContext(), error.toString())
            }
            adminRequestAdapter.notifyItemRemoved(position)
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(requireContext(), e.toString())
        }
    }

    private fun fetchUsers() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                adminRequestDataList.clear()
                for (userSnapshot in snapshot.children) {
                    val unm = userSnapshot.child("unm").getValue(String::class.java)
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val password = userSnapshot.child("password").getValue(String::class.java)
                    val confirm_password =
                        userSnapshot.child("confirm_password").getValue(String::class.java)

                    if (unm != null && email != null && password != null) {
                        val adminRequestData = AdminRequestData(
                            email, password, confirm_password.toString(), unm
                        )
                        adminRequestDataList.add(adminRequestData)
                    }
                }
                adminRequestAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}