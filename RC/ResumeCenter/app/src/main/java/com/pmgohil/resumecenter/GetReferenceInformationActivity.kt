package com.pmgohil.resumecenter

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GetReferenceInformationActivity : AppCompatActivity() {

    lateinit var llReferenceMain1: LinearLayout
    lateinit var txtReference1: TextView
    lateinit var downArrowReference1: ImageView
    lateinit var upArrowReference1: ImageView
    lateinit var llReference1: LinearLayout
    lateinit var etName1Reference: EditText
    lateinit var etCompanyName1Reference: EditText
    lateinit var etPosition1Reference: EditText
    lateinit var etPhone1Reference: EditText
    lateinit var etLocation1Reference: EditText
    lateinit var llReferenceMain2: LinearLayout
    lateinit var etName2Reference: EditText
    lateinit var etCompanyName2Reference: EditText
    lateinit var etPosition2Reference: EditText
    lateinit var etPhone2Reference: EditText
    lateinit var etLocation2Reference: EditText
    lateinit var llReferenceMain3: LinearLayout
    lateinit var etName3Reference: EditText
    lateinit var etCompanyName3Reference: EditText
    lateinit var etPosition3Reference: EditText
    lateinit var etPhone3Reference: EditText
    lateinit var etLocation3Reference: EditText
    lateinit var llReferenceMain4: LinearLayout
    lateinit var etName4Reference: EditText
    lateinit var etCompanyName4Reference: EditText
    lateinit var etPosition4Reference: EditText
    lateinit var etPhone4Reference: EditText
    lateinit var etLocation4Reference: EditText
    lateinit var llReferenceMain5: LinearLayout
    lateinit var etName5Reference: EditText
    lateinit var etCompanyName5Reference: EditText
    lateinit var etPosition5Reference: EditText
    lateinit var etPhone5Reference: EditText
    lateinit var etLocation5Reference: EditText
    lateinit var llReferenceMain6: LinearLayout
    lateinit var etName6Reference: EditText
    lateinit var etCompanyName6Reference: EditText
    lateinit var etPosition6Reference: EditText
    lateinit var etPhone6Reference: EditText
    lateinit var etLocation6Reference: EditText
    lateinit var llReferenceMain7: LinearLayout
    lateinit var etName7Reference: EditText
    lateinit var etCompanyName7Reference: EditText
    lateinit var etPosition7Reference: EditText
    lateinit var etPhone7Reference: EditText
    lateinit var etLocation7Reference: EditText
    lateinit var llReferenceMain8: LinearLayout
    lateinit var etName8Reference: EditText
    lateinit var etCompanyName8Reference: EditText
    lateinit var etPosition8Reference: EditText
    lateinit var etPhone8Reference: EditText
    lateinit var etLocation8Reference: EditText
    lateinit var llReferenceMain9: LinearLayout
    lateinit var etName9Reference: EditText
    lateinit var etCompanyName9Reference: EditText
    lateinit var etPosition9Reference: EditText
    lateinit var etPhone9Reference: EditText
    lateinit var etLocation9Reference: EditText
    lateinit var llReferenceMain10: LinearLayout
    lateinit var etName10Reference: EditText
    lateinit var etCompanyName10Reference: EditText
    lateinit var etPosition10Reference: EditText
    lateinit var etPhone10Reference: EditText
    lateinit var etLocation10Reference: EditText
    lateinit var skipRL: RelativeLayout
    lateinit var nextRL: RelativeLayout
    lateinit var email: String
    lateinit var number: String
    lateinit var myDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_reference_information)
        llReferenceMain1 = findViewById(R.id.llReferenceMain1)
        txtReference1 = findViewById(R.id.txtReference1)
        downArrowReference1 = findViewById(R.id.downArrowReference1)
        upArrowReference1 = findViewById(R.id.upArrowReference1)
        llReference1 = findViewById(R.id.llReference1)
        etName1Reference = findViewById(R.id.etName1Reference)
        etCompanyName1Reference = findViewById(R.id.etCompanyName1Reference)
        etPosition1Reference = findViewById(R.id.etPosition1Reference)
        etPhone1Reference = findViewById(R.id.etPhone1Reference)
        etLocation1Reference = findViewById(R.id.etLocation1Reference)
        llReferenceMain2 = findViewById(R.id.llReferenceMain2)
        etName2Reference = findViewById(R.id.etName2Reference)
        etCompanyName2Reference = findViewById(R.id.etCompanyName2Reference)
        etPosition2Reference = findViewById(R.id.etPosition2Reference)
        etPhone2Reference = findViewById(R.id.etPhone2Reference)
        etLocation2Reference = findViewById(R.id.etLocation2Reference)
        llReferenceMain3 = findViewById(R.id.llReferenceMain3)
        etName3Reference = findViewById(R.id.etName3Reference)
        etCompanyName3Reference = findViewById(R.id.etCompanyName3Reference)
        etPosition3Reference = findViewById(R.id.etPosition3Reference)
        etPhone3Reference = findViewById(R.id.etPhone3Reference)
        etLocation3Reference = findViewById(R.id.etLocation3Reference)
        llReferenceMain4 = findViewById(R.id.llReferenceMain4)
        etName4Reference = findViewById(R.id.etName4Reference)
        etCompanyName4Reference = findViewById(R.id.etCompanyName4Reference)
        etPosition4Reference = findViewById(R.id.etPosition4Reference)
        etPhone4Reference = findViewById(R.id.etPhone4Reference)
        etLocation4Reference = findViewById(R.id.etLocation4Reference)
        llReferenceMain5 = findViewById(R.id.llReferenceMain5)
        etName5Reference = findViewById(R.id.etName5Reference)
        etCompanyName5Reference = findViewById(R.id.etCompanyName5Reference)
        etPosition5Reference = findViewById(R.id.etPosition5Reference)
        etPhone5Reference = findViewById(R.id.etPhone5Reference)
        etLocation5Reference = findViewById(R.id.etLocation5Reference)
        llReferenceMain6 = findViewById(R.id.llReferenceMain6)
        etName6Reference = findViewById(R.id.etName6Reference)
        etCompanyName6Reference = findViewById(R.id.etCompanyName6Reference)
        etPosition6Reference = findViewById(R.id.etPosition6Reference)
        etPhone6Reference = findViewById(R.id.etPhone6Reference)
        etLocation6Reference = findViewById(R.id.etLocation6Reference)
        llReferenceMain7 = findViewById(R.id.llReferenceMain7)
        etName7Reference = findViewById(R.id.etName7Reference)
        etCompanyName7Reference = findViewById(R.id.etCompanyName7Reference)
        etPosition7Reference = findViewById(R.id.etPosition7Reference)
        etPhone7Reference = findViewById(R.id.etPhone7Reference)
        etLocation7Reference = findViewById(R.id.etLocation7Reference)
        llReferenceMain8 = findViewById(R.id.llReferenceMain8)
        etName8Reference = findViewById(R.id.etName8Reference)
        etCompanyName8Reference = findViewById(R.id.etCompanyName8Reference)
        etPosition8Reference = findViewById(R.id.etPosition8Reference)
        etPhone8Reference = findViewById(R.id.etPhone8Reference)
        etLocation8Reference = findViewById(R.id.etLocation8Reference)
        llReferenceMain9 = findViewById(R.id.llReferenceMain9)
        etName9Reference = findViewById(R.id.etName9Reference)
        etCompanyName9Reference = findViewById(R.id.etCompanyName9Reference)
        etPosition9Reference = findViewById(R.id.etPosition9Reference)
        etPhone9Reference = findViewById(R.id.etPhone9Reference)
        etLocation9Reference = findViewById(R.id.etLocation9Reference)
        llReferenceMain10 = findViewById(R.id.llReferenceMain10)
        etName10Reference = findViewById(R.id.etName10Reference)
        etCompanyName10Reference = findViewById(R.id.etCompanyName10Reference)
        etPosition10Reference = findViewById(R.id.etPosition10Reference)
        etPhone10Reference = findViewById(R.id.etPhone10Reference)
        etLocation10Reference = findViewById(R.id.etLocation10Reference)
        skipRL = findViewById(R.id.skipRL)
        nextRL = findViewById(R.id.nextRL)
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("ReferenceData")

        downArrowReference1.visibility = View.VISIBLE
        llReferenceMain1.setOnClickListener() {
            visibilityTrue()
        }

        etLocation1Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation1Reference.length() >= 5) {
                    llReferenceMain2.visibility = View.VISIBLE
                } else {
                    llReferenceMain2.visibility = View.GONE
                    llReferenceMain3.visibility = View.GONE
                    llReferenceMain4.visibility = View.GONE
                    llReferenceMain5.visibility = View.GONE
                    llReferenceMain6.visibility = View.GONE
                    llReferenceMain7.visibility = View.GONE
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))

        etLocation2Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation2Reference.length() >= 5) {
                    llReferenceMain3.visibility = View.VISIBLE
                } else {
                    llReferenceMain3.visibility = View.GONE
                    llReferenceMain4.visibility = View.GONE
                    llReferenceMain5.visibility = View.GONE
                    llReferenceMain6.visibility = View.GONE
                    llReferenceMain7.visibility = View.GONE
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))
        etLocation3Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation3Reference.length() >= 5) {
                    llReferenceMain4.visibility = View.VISIBLE
                } else {
                    llReferenceMain4.visibility = View.GONE
                    llReferenceMain5.visibility = View.GONE
                    llReferenceMain6.visibility = View.GONE
                    llReferenceMain7.visibility = View.GONE
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))

        etLocation4Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation4Reference.length() >= 5) {
                    llReferenceMain5.visibility = View.VISIBLE
                } else {
                    llReferenceMain5.visibility = View.GONE
                    llReferenceMain6.visibility = View.GONE
                    llReferenceMain7.visibility = View.GONE
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))
        etLocation5Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation5Reference.length() >= 5) {
                    llReferenceMain6.visibility = View.VISIBLE
                } else {
                    llReferenceMain6.visibility = View.GONE
                    llReferenceMain7.visibility = View.GONE
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))

        etLocation6Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation6Reference.length() >= 5) {
                    llReferenceMain7.visibility = View.VISIBLE
                } else {
                    llReferenceMain7.visibility = View.GONE
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))
        etLocation7Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation7Reference.length() >= 5) {
                    llReferenceMain8.visibility = View.VISIBLE
                } else {
                    llReferenceMain8.visibility = View.GONE
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))
        etLocation8Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation8Reference.length() >= 5) {
                    llReferenceMain9.visibility = View.VISIBLE
                } else {
                    llReferenceMain9.visibility = View.GONE
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))
        etLocation9Reference.addTextChangedListener((object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (etLocation9Reference.length() >= 5) {
                    llReferenceMain10.visibility = View.VISIBLE
                } else {
                    llReferenceMain10.visibility = View.GONE
                }
            }
        }))


        number = intent.getStringExtra("number").toString()
        email = intent.getStringExtra("email").toString()
        skipRL.setOnClickListener() {
            val experienceIntent = Intent(this, TemplateActivity::class.java)
            experienceIntent.putExtra("number", number)
            experienceIntent.putExtra("email", email)
            startActivity(experienceIntent)
        }
        nextRL.setOnClickListener() {
            insertData()
        }
    }

    fun insertData() {
        if (etName1Reference.text.isNotEmpty()) {
            var RDM = ReferenceDataModel(
                etName1Reference.text.toString(),
                etCompanyName1Reference.text.toString(),
                etPosition1Reference.text.toString(),
                etPhone1Reference.text.toString(),
                etLocation1Reference.text.toString(),
                etName2Reference.text.toString(),
                etCompanyName2Reference.text.toString(),
                etPosition2Reference.text.toString(),
                etPhone2Reference.text.toString(),
                etLocation2Reference.text.toString(),
                etName3Reference.text.toString(),
                etCompanyName3Reference.text.toString(),
                etPosition3Reference.text.toString(),
                etPhone3Reference.text.toString(),
                etLocation3Reference.text.toString(),
                etName4Reference.text.toString(),
                etCompanyName4Reference.text.toString(),
                etPosition4Reference.text.toString(),
                etPhone4Reference.text.toString(),
                etLocation4Reference.text.toString(),
                etName5Reference.text.toString(),
                etCompanyName5Reference.text.toString(),
                etPosition5Reference.text.toString(),
                etPhone5Reference.text.toString(),
                etLocation5Reference.text.toString(),
                etName6Reference.text.toString(),
                etCompanyName6Reference.text.toString(),
                etPosition6Reference.text.toString(),
                etPhone6Reference.text.toString(),
                etLocation6Reference.text.toString(),
                etName7Reference.text.toString(),
                etCompanyName7Reference.text.toString(),
                etPosition7Reference.text.toString(),
                etPhone7Reference.text.toString(),
                etLocation7Reference.text.toString(),
                etName8Reference.text.toString(),
                etCompanyName8Reference.text.toString(),
                etPosition8Reference.text.toString(),
                etPhone8Reference.text.toString(),
                etLocation8Reference.text.toString(),
                etName9Reference.text.toString(),
                etCompanyName9Reference.text.toString(),
                etPosition9Reference.text.toString(),
                etPhone9Reference.text.toString(),
                etLocation9Reference.text.toString(),
                etName10Reference.text.toString(),
                etCompanyName10Reference.text.toString(),
                etPosition10Reference.text.toString(),
                etPhone10Reference.text.toString(),
                etLocation10Reference.text.toString()
            )
            myDatabaseReference.child(number).setValue(RDM)
                .addOnSuccessListener {
                    val referenceIntent =
                        Intent(this, TemplateActivity::class.java)
                    referenceIntent.putExtra("number", number)
                    referenceIntent.putExtra("email", email)
                    startActivity(referenceIntent)
                }.addOnFailureListener { Toast(this).showCustomToast("Something Wrong!", this) }
        } else {
            Toast(this).showCustomToast("Please, Fill the Value !", this)
        }
    }

    fun visibilityTrue() {
        txtReference1.setTextColor(resources.getColor(R.color.pink))
        downArrowReference1.visibility = View.GONE
        upArrowReference1.visibility = View.VISIBLE
        llReference1.visibility = View.VISIBLE
    }

    fun visibilityFalse() {
        txtReference1.setTextColor(resources.getColor(R.color.purple))
        llReferenceMain1.visibility = View.GONE
        llReference1.visibility = View.GONE
        llReferenceMain2.visibility = View.GONE
        llReferenceMain3.visibility = View.GONE
        llReferenceMain4.visibility = View.GONE
        llReferenceMain5.visibility = View.GONE
        llReferenceMain6.visibility = View.GONE
        llReferenceMain7.visibility = View.GONE
        llReferenceMain8.visibility = View.GONE
        llReferenceMain9.visibility = View.GONE
        llReferenceMain10.visibility = View.GONE
    }
}