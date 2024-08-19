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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class SelectThoughtsFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var selectThoughtsAdapter: SelectThoughtsAdapter
    private lateinit var thoughtsList: MutableList<ThoughtsData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_thoughts, container, false)

        database = FirebaseDatabase.getInstance().reference.child("thoughts_data")
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        thoughtsList = mutableListOf()
        selectThoughtsAdapter = SelectThoughtsAdapter(requireContext(), thoughtsList, database)
        recyclerView.adapter = selectThoughtsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchThoughts()

        return view
    }

    private fun fetchThoughts() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                thoughtsList.clear()
                for (thoughtsSnapshot in snapshot.children) {
                    val thoughts = thoughtsSnapshot.child("thoughts").getValue(String::class.java)
                    val key = thoughtsSnapshot.key
                    //GlobalVariableFunction.showToast(requireContext(), key.toString())
                    val thoughtsData = ThoughtsData(
                        key.toString(),
                        thoughts.toString()
                    )
                    thoughtsList.add(thoughtsData)

                }
                selectThoughtsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

    }
    /*

        private fun deleteThoughts(position: Int) {
            try {
                val thoughts = thoughtsList[position]
                val userReference = database.child(thoughts.thoughts)
                userReference.removeValue().addOnSuccessListener {
                    try {
                        thoughtsList.removeAt(position)
                    } catch (e: Exception) {
                        GlobalVariableFunction.showToast(requireContext(), e.toString())
                    }
                }.addOnFailureListener { error ->
                    GlobalVariableFunction.showToast(requireContext(), error.toString())
                }
                selectThoughtsAdapter.notifyItemRemoved(position)
            } catch (e: Exception) {
                GlobalVariableFunction.showToast(requireContext(), e.toString())
            }
        }
    */

}