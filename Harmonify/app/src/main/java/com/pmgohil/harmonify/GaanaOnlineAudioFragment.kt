package com.pmgohil.harmonify

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class GaanaOnlineAudioFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var gaanaOnlineAudioAdapter: GaanaOnlineAudioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gaana_online_audio, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recommendations_list_online)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchTracks()
    }

    private fun fetchTracks() {
        lifecycleScope.launch {
            try {
                val response = GaanaRetrofitInstance.api.getTracks()
                if (response.isSuccessful) {
                    Log.e("H45", "Prakash Gohil")
                    Log.e("H45", "Response Body: ${response.body()}")
                    Toast.makeText(requireContext(), "${response.body()}", Toast.LENGTH_SHORT)
                        .show()

                    response.body()?.let {
                        gaanaOnlineAudioAdapter = GaanaOnlineAudioAdapter(it.tracks)
                        recyclerView.adapter = gaanaOnlineAudioAdapter
                    }
                } else {
                    Log.e("H45", "Response Code: ${response.code()}")
                    Log.e("H45", "Response Message: ${response.message()}")
                    Log.e("H45", "Else: Response Body: ${response.errorBody()?.string()}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("H45", "Response Body: $e")
                Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
            }
        }
    }


}