package com.pmgohil.thoughtsadmin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SelectImageFragment : Fragment() {
    private lateinit var storageReference: StorageReference
    private lateinit var selectImageAdapter: SelectImageAdapter
    private lateinit var imageList: MutableList<ImageModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_image, container, false)
        storageReference = FirebaseStorage.getInstance().reference.child("images")

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        imageList = mutableListOf()
        selectImageAdapter = SelectImageAdapter(imageList) { position ->
            val imageModel = imageList[position]
            deleteImage(imageModel)
        }
        recyclerView.adapter = selectImageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchImages()

        return view
    }

    private fun deleteImage(imageModel: ImageModel) {
        try {
            val imageId = getImageIdFromModel(imageModel)
            storageReference.child("$imageId").delete().addOnSuccessListener {
                try {
                    imageList.remove(imageModel)
                    selectImageAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    GlobalVariableFunction.showToast(requireContext(), e.toString())
                }
            }.addOnFailureListener { exception ->
                GlobalVariableFunction.showToast(requireContext(), exception.toString())
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(requireContext(), e.toString())
        }
    }

    private fun getImageIdFromModel(imageModel: ImageModel): String {
        return imageModel.imageId
    }

    private fun fetchImages() {
        try {
            val listTask = storageReference.listAll()
            listTask.addOnSuccessListener { result ->
                for (item in result.items) {
                    item.downloadUrl.addOnSuccessListener { downloadUrl ->
                        val imageModel = ImageModel(downloadUrl.toString(), item.name)
                        imageList.add(imageModel)
                        selectImageAdapter.notifyDataSetChanged()
                    }.addOnFailureListener { exception ->
                        GlobalVariableFunction.showToast(requireContext(), exception.toString())
                    }
                }
            }.addOnFailureListener { exception ->
                GlobalVariableFunction.showToast(requireContext(), exception.toString())
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(requireContext(), e.toString())
        }
    }
}
