package com.pmgohil.resumecenter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class GetPersonalInfoActivity : AppCompatActivity() {

    lateinit var myDatabaseReference: DatabaseReference
    lateinit var myFirebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var llSelectImage: LinearLayout
    lateinit var selectImage: TextView
    lateinit var imageView: ImageView
    lateinit var filePath: Uri
    private val PICK_IMAGE_REQUEST = 22
    lateinit var nm: EditText
    lateinit var email: EditText
    lateinit var num: EditText
    lateinit var address: EditText
    lateinit var gender: RadioGroup
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var imgDatePicker: ImageView
    lateinit var datePicker: DatePicker
    lateinit var birthDate: EditText
    lateinit var birthPlace: EditText
    lateinit var religion: EditText
    lateinit var cast: EditText
    lateinit var maritalStatusRadio: RadioGroup
    lateinit var married: RadioButton
    lateinit var unmarried: RadioButton
    lateinit var etChild: EditText
    lateinit var englishLanguage: CheckBox
    lateinit var hindiLanguage: CheckBox
    lateinit var otherLanguage: EditText
    lateinit var websiteUrl: EditText
    lateinit var nationality: EditText
    lateinit var vilage: EditText
    lateinit var taluka: EditText
    lateinit var district: EditText
    lateinit var state: EditText
    lateinit var country: EditText
    lateinit var genderResult: String
    lateinit var maritalStatusResult: String
    lateinit var languageResult: String
    lateinit var registrationCardBtn: CardView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_personal_info)

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("RegistrationData")
        myFirebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = myFirebaseAuth.currentUser!!
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        llSelectImage = findViewById(R.id.llSelectImage)
        selectImage = findViewById(R.id.txtSelectImage)
        imageView = findViewById(R.id.imageView)
        llSelectImage.requestFocus()
        nm = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        num = findViewById(R.id.etNumber)
        address = findViewById(R.id.etAddress)
        gender = findViewById(R.id.genderRadio)
        male = findViewById(R.id.male)
        female = findViewById(R.id.female)
        imgDatePicker = findViewById(R.id.imgDatePicker)
        datePicker = findViewById(R.id.datePicker)
        birthDate = findViewById(R.id.etBirthDate)
        birthPlace = findViewById(R.id.etBirthPlace)
        religion = findViewById(R.id.etReligion)
        cast = findViewById(R.id.etCast)
        maritalStatusRadio = findViewById(R.id.msRadio)
        married = findViewById(R.id.married)
        unmarried = findViewById(R.id.unmarried)
        etChild = findViewById(R.id.etChild)
        englishLanguage = findViewById(R.id.englishLanguage)
        hindiLanguage = findViewById(R.id.hindiLanguage)
        otherLanguage = findViewById(R.id.etOtherLanguage)
        websiteUrl = findViewById(R.id.etWebsite)
        nationality = findViewById(R.id.etNationality)
        vilage = findViewById(R.id.etVilage)
        taluka = findViewById(R.id.etTaluka)
        district = findViewById(R.id.etDistrict)
        state = findViewById(R.id.etState)
        country = findViewById(R.id.etCountry)

        llSelectImage.setOnClickListener() { SelectImage() }

        registrationCardBtn = findViewById(R.id.registrationCard)
        registrationCardBtn.setOnClickListener() { insertData() }

        imgDatePicker.setOnClickListener() {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            try {
                val dpd = DatePickerDialog(
                    this, R.style.DialogTheme, { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val printDate =
                            (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        birthDate.setText(printDate)
                    }, year, month, day
                )
                dpd.show()
            } catch (e: java.lang.Exception) {
                Toast(this).showCustomToast("$e", this)
            }

        }
        married.setOnClickListener() {
            if (married.isChecked) {
                etChild.visibility = View.VISIBLE
                etChild.requestFocus()
            }
        }
        unmarried.setOnClickListener() {
            if (unmarried.isChecked) {
                etChild.visibility = View.GONE
            }
        }

    }

    private fun insertData() {

        getGenderValue()
        getMaritalStatusValue()
        getLanguageValue()

        val nm = nm.text.toString()
        val email = email.text.toString()
        var num = num.text.toString()
        val address = address.text.toString()
        val genderResult = genderResult
        val birthDate = birthDate.text.toString()
        val birthPlace = birthPlace.text.toString()
        val religion = religion.text.toString()
        val cast = cast.text.toString()
        val maritialStatus = maritalStatusResult
        val child = etChild.text.toString()
        val languageResult = languageResult
        val otherLanguage = otherLanguage.text.toString()
        val nationality = nationality.text.toString()
        val vilage = vilage.text.toString()
        val taluka = taluka.text.toString()
        val district = district.text.toString()
        val state = state.text.toString()
        val country = country.text.toString()
        if (num.length == 10) {
            if (nm.isNotEmpty() && email.isNotEmpty() && num.isNotEmpty() && address.isNotEmpty() && genderResult.isNotEmpty() && birthDate.isNotEmpty() && birthPlace.isNotEmpty() && religion.isNotEmpty() && cast.isNotEmpty() && maritialStatus.isNotEmpty() && languageResult.isNotEmpty() && nationality.isNotEmpty() && vilage.isNotEmpty() && taluka.isNotEmpty() && district.isNotEmpty() && state.isNotEmpty() && country.isNotEmpty()) {
                emailValidator()
                val UDM = UserDataModel(
                    nm,
                    email,
                    num,
                    address,
                    genderResult,
                    birthDate,
                    birthPlace,
                    religion,
                    cast,
                    maritialStatus,
                    child,
                    languageResult,
                    otherLanguage,
                    websiteUrl.text.toString(),
                    nationality,
                    vilage,
                    taluka,
                    district,
                    state,
                    country
                )
                myDatabaseReference.child(num).setValue(UDM).addOnSuccessListener {
                    uploadImage()
                    val intent = Intent(this, GetEducationalInfoActivity::class.java)
                    intent.putExtra("number", num)
                    intent.putExtra("email", email)
                    startActivity(intent)

                }.addOnFailureListener { Toast(this).showCustomToast("Something Wrong!", this) }
            } else {
                Toast(this).showCustomToast("Please, Fill the data!", this)
            }
        } else {
            Toast(this).showCustomToast("Number must be a 10 digit.!", this)
        }
    }

    //get gender from radio button control
    fun getGenderValue() {
        val id: Int = gender.checkedRadioButtonId
        if (id >= 1) {
            val radio: RadioButton = findViewById(id)
            genderResult = "${radio.text}"
            //Toast(this).showCustomToast(genderResult, this)
        } else {
            Toast(this).showCustomToast("Please, Select your gender..!", this)
        }
    }

    fun getMaritalStatusValue() {
        val id: Int = maritalStatusRadio.checkedRadioButtonId
        if (id >= 1) {
            val radio: RadioButton = findViewById(id)
            maritalStatusResult = "${radio.text}"
            //Toast(this).showCustomToast(genderResult, this)
        } else {
            Toast(this).showCustomToast("Please, Select your Marital Status..!", this)
        }
    }

    //get language from checkbox control
    fun getLanguageValue() {
        if (hindiLanguage.isChecked() && englishLanguage.isChecked()) {
            languageResult = "English, Hindi"
        } else if (englishLanguage.isChecked()) {
            languageResult = "English"
        } else if (hindiLanguage.isChecked()) {
            languageResult = "Hindi"
        } else {
            Toast(this).showCustomToast("Please, Select the Language", this)
        }
        //Toast(this).showCustomToast(languageResult, this)
    }

    fun emailValidator() {
        if (email.text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            //Toast(this).showCustomToast("Email Verified !", this)
        } else {
            Toast(this).showCustomToast("Enter a valid Email address !", this)
        }
    }

    // Select Image method
    @SuppressLint("SetTextI18n")
    private fun SelectImage() {

        // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent, "Select Image from here..."
            ), PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(
            requestCode, resultCode, data
        )

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data!!
            try {
                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver, filePath
                )
                imageView.setImageBitmap(bitmap)
                if (imageView != null && filePath != null) {
                    //uploadImage()
                    selectImage.setTextColor(Color.parseColor("#0AAEC6"))
                    selectImage.text = "Image Selected."
                }
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }

    fun uploadImage() {

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            //var file = Uri.fromFile(File("images/.jpg"))
            val imageNickName = num.text.toString()
            val ref =
                storageReference.child("images/" + "$imageNickName")//+ UUID.randomUUID().toString()

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener { // Image uploaded successfully
                // Dismiss dialog
                progressDialog.dismiss()
                //Toast(this).showCustomToast("uploaded", this)
            }.addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog.dismiss()
                Toast(this).showCustomToast("Iamge Upload Fail : $e", this)
            }.addOnProgressListener { taskSnapshot ->

                // Progress Listener for loading
                // percentage on the dialog box
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
        } else {
            Toast(this).showCustomToast("Image Not Selected.", this)
        }
    }
}