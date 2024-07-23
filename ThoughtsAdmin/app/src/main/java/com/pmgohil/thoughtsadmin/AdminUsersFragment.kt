package com.pmgohil.thoughtsadmin

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

class AdminUsersFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var adminUsersAdapter: AdminUsersAdapter
    private lateinit var adminUsersList: MutableList<AdminUsersData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_users, container, false)

        database = FirebaseDatabase.getInstance().reference.child("admin_user")
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adminUsersList = mutableListOf()
        adminUsersAdapter = AdminUsersAdapter(requireContext(), adminUsersList) { position ->
            deleteUser(position)
        }
        recyclerView.adapter = adminUsersAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        fetchUsers()

        return view
    }

    private fun deleteUser(position: Int) {
        try {
            val admin_users = adminUsersList[position]
            val userReference = database.child(admin_users.unm)
            userReference.removeValue().addOnSuccessListener {
                try {
                    adminUsersList.removeAt(position)
                } catch (e: Exception) {
                    GlobalVariableFunction.showToast(requireContext(), e.toString())
                }
            }.addOnFailureListener { error ->
                GlobalVariableFunction.showToast(requireContext(), error.toString())
            }
            adminUsersAdapter.notifyItemRemoved(position)
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(requireContext(), e.toString())
        }
    }

    private fun fetchUsers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adminUsersList.clear()
                for (userSnapshot in snapshot.children) {
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val password = userSnapshot.child("password").getValue(String::class.java)
                    val role = userSnapshot.child("role").getValue(String::class.java)
                    val unm = userSnapshot.child("unm").getValue(String::class.java)

                    if (email != null && password != null && role != null && unm != null) {
                        val adminUsersData = AdminUsersData(
                            email, password, role, unm
                        )
                        adminUsersList.add(adminUsersData)
                    }
                }
                adminUsersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}
