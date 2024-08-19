package com.pmgohil.thoughtsadmin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class UploadImageFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var addImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var uploadImageButton: Button
    private lateinit var et_write_thoughts: TextView
    private lateinit var btn_submit_thoughts: Button
    private lateinit var spinner: Spinner
    private var selectedImageUri: Uri? = null
    lateinit var selected_spinner_item: String
    lateinit var real_selected_item: String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_image, container, false)
        imageView = view.findViewById(R.id.imageView)
        addImageView = view.findViewById(R.id.addImageView)
        selectImageButton = view.findViewById(R.id.selectImageButton)
        uploadImageButton = view.findViewById(R.id.uploadImageButton)
        et_write_thoughts = view.findViewById(R.id.et_write_thoughts)
        btn_submit_thoughts = view.findViewById(R.id.btn_submit_thoughts)
        spinner = view.findViewById(R.id.spinner)

        selectImageButton.setOnClickListener {
            openFilePicker()
        }

        uploadImageButton.setOnClickListener {
            selectedImageUri?.let { uri ->
                uploadImageToFirebase(uri)
            }
        }
        btn_submit_thoughts.setOnClickListener {
            insert_thoughts_in_firebase_database()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(), R.array.array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // Set a listener to handle item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long
            ) {
                // Handle the selected item
                real_selected_item = parentView.getItemAtPosition(position).toString()
                val lowercaseString = real_selected_item.lowercase()
                selected_spinner_item = lowercaseString.replace(" ", "_")

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle nothing selected
            }
        }
    }

    private fun insert_thoughts_in_firebase_database() {
        try {
            if (et_write_thoughts.text.toString().isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val reference = database.reference

                if (real_selected_item != getString(R.string.click_here_to_select_category)) {

                    val sanitizedSpinnerItem =
                        URLEncoder.encode(selected_spinner_item, StandardCharsets.UTF_8.toString())
                    val sanitizedThoughtsText = URLEncoder.encode(
                        et_write_thoughts.text.toString(), StandardCharsets.UTF_8.toString()
                    )

                    val child_string: String =
                        "$sanitizedSpinnerItem - $sanitizedThoughtsText" //selected_spinner_item + " - " + et_write_thoughts.text.toString()
                    val thoughtsData = ThoughtsData(child_string, et_write_thoughts.text.toString())
                    //.child(selected_spinner_item)
                    reference.child("thoughts_data").child(child_string).setValue(thoughtsData)
                        .addOnSuccessListener {
                            et_write_thoughts.text = ""
                            GlobalVariableFunction.showToast(requireContext(), "Thoughts Inserted")
                        }.addOnFailureListener { exception ->
                            Log.e("prakash-2105", "$exception")
                            GlobalVariableFunction.showToast(
                                requireContext(), "$exception Data not insert"
                            )
                        }
                } else {
                    GlobalVariableFunction.showToast(requireContext(), "Please Select a Category!")
                }
            } else {
                GlobalVariableFunction.showToast(requireContext(), "Give Your Thoughts!")
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(requireContext(), "Give Your Thoughts!")
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
        addImageView.visibility = View.GONE
        imageView.visibility = View.VISIBLE
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (selectedImageUri != null) {
            val storage = Firebase.storage
            val min = 100_000_000_00L // 10 digits
            val max = 999_999_999_99L // 11 digits
            if (real_selected_item != getString(R.string.click_here_to_select_category)) {
                val imageNickName =
                    selected_spinner_item + " " + Random.nextLong(min, max).toString()

                val progressDialog = ProgressDialog(requireContext())
                progressDialog.setTitle("Uploading...")
                progressDialog.show()
                val ref =
                    storage.reference.child("images/" + "$imageNickName")//+ UUID.randomUUID().toString()

                ref.putFile(fileUri).addOnSuccessListener {
                    progressDialog.dismiss()
                    selectedImageUri = null
                    imageView.visibility = View.GONE
                    addImageView.visibility = View.VISIBLE
                }.addOnFailureListener { e ->
                    progressDialog.dismiss()
                    GlobalVariableFunction.showToast(requireContext(), "Iamge Upload Fail : $e")
                }.addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
            } else {
                GlobalVariableFunction.showToast(requireContext(), "Please Select a Category!")
            }
        } else {
            GlobalVariableFunction.showToast(requireContext(), "Please Select the image.")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageView.setImageURI(selectedImageUri)


        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
