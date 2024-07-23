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

class UsersFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: MutableList<UserData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        database = FirebaseDatabase.getInstance().reference.child("users")
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        userList = mutableListOf()
        userAdapter = UserAdapter(requireContext(), userList) { position ->
            deleteUser(position)
        }
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        fetchUsers()

        return view
    }

    private fun deleteUser(position: Int) {
        try {
            val user = userList[position]
            val userReference = database.child(user.unm)
            userReference.removeValue().addOnSuccessListener {
                try {
                    userList.removeAt(position)
                } catch (e: Exception) {
                    //GlobalVariableFunction.showToast(requireContext(), e.toString())
                }
            }.addOnFailureListener { error ->
                GlobalVariableFunction.showToast(requireContext(), error.toString())
            }
            userAdapter.notifyItemRemoved(position)
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(requireContext(), e.toString())
        }
    }

    private fun fetchUsers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val name = userSnapshot.child("name").getValue(String::class.java)
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val unm = userSnapshot.child("unm").getValue(String::class.java)
                    val mail_id_image =
                        userSnapshot.child("mail_id_image").getValue(String::class.java)
                    val internal_image =
                        userSnapshot.child("internal_image").getValue(String::class.java)

                    if (name != null && email != null && mail_id_image != null && unm != null && internal_image != null) {
                        val userData = UserData(
                            name, email, unm.toString(), mail_id_image, internal_image.toString()
                        )
                        userList.add(userData)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}
