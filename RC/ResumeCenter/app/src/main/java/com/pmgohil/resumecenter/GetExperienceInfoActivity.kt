package com.pmgohil.resumecenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GetExperienceInfoActivity : AppCompatActivity() {

    lateinit var fresher: CheckBox
    lateinit var fresherResult: String
    lateinit var llExpMain1: LinearLayout
    lateinit var txtExp1: TextView
    lateinit var downArrowExp1: ImageView
    lateinit var upArrowExp1: ImageView
    lateinit var llExp1: LinearLayout
    lateinit var etJobTitle1: EditText
    lateinit var etPosition1: EditText
    lateinit var etCompanyName1: EditText
    lateinit var etLocation1: EditText
    lateinit var llExpMain2: LinearLayout
    lateinit var etJobTitle2: EditText
    lateinit var etPosition2: EditText
    lateinit var etCompanyName2: EditText
    lateinit var etLocation2: EditText
    lateinit var llExpMain3: LinearLayout
    lateinit var etJobTitle3: EditText
    lateinit var etPosition3: EditText
    lateinit var etCompanyName3: EditText
    lateinit var etLocation3: EditText
    lateinit var llExpMain4: LinearLayout
    lateinit var etJobTitle4: EditText
    lateinit var etPosition4: EditText
    lateinit var etCompanyName4: EditText
    lateinit var etLocation4: EditText
    lateinit var llExpMain5: LinearLayout
    lateinit var etJobTitle5: EditText
    lateinit var etPosition5: EditText
    lateinit var etCompanyName5: EditText
    lateinit var etLocation5: EditText
    lateinit var llExpMain6: LinearLayout
    lateinit var etJobTitle6: EditText
    lateinit var etPosition6: EditText
    lateinit var etCompanyName6: EditText
    lateinit var etLocation6: EditText
    lateinit var llExpMain7: LinearLayout
    lateinit var etJobTitle7: EditText
    lateinit var etPosition7: EditText
    lateinit var etCompanyName7: EditText
    lateinit var etLocation7: EditText
    lateinit var llExpMain8: LinearLayout
    lateinit var etJobTitle8: EditText
    lateinit var etPosition8: EditText
    lateinit var etCompanyName8: EditText
    lateinit var etLocation8: EditText
    lateinit var llExpMain9: LinearLayout
    lateinit var etJobTitle9: EditText
    lateinit var etPosition9: EditText
    lateinit var etCompanyName9: EditText
    lateinit var etLocation9: EditText
    lateinit var llExpMain10: LinearLayout
    lateinit var etJobTitle10: EditText
    lateinit var etPosition10: EditText
    lateinit var etCompanyName10: EditText
    lateinit var etLocation10: EditText
    lateinit var skipRL: RelativeLayout
    lateinit var nextRL: RelativeLayout
    lateinit var myDatabaseReference: DatabaseReference
    lateinit var number: String
    lateinit var email: String

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_experience_info)

        fresher = findViewById(R.id.fresher)
        llExpMain1 = findViewById(R.id.llExpMain1)
        txtExp1 = findViewById(R.id.txtExp1)
        downArrowExp1 = findViewById(R.id.downArrowExp1)
        upArrowExp1 = findViewById(R.id.upArrowExp1)
        llExp1 = findViewById(R.id.llExp1)
        etJobTitle1 = findViewById(R.id.etJobTitle1)
        etPosition1 = findViewById(R.id.etPosition1)
        etCompanyName1 = findViewById(R.id.etCompanyName1)
        etLocation1 = findViewById(R.id.etLocation1)
        llExpMain2 = findViewById(R.id.llExpMain2)
        etJobTitle2 = findViewById(R.id.etJobTitle2)
        etPosition2 = findViewById(R.id.etPosition2)
        etCompanyName2 = findViewById(R.id.etCompanyName2)
        etLocation2 = findViewById(R.id.etLocation2)
        llExpMain3 = findViewById(R.id.llExpMain3)
        etJobTitle3 = findViewById(R.id.etJobTitle3)
        etPosition3 = findViewById(R.id.etPosition3)
        etCompanyName3 = findViewById(R.id.etCompanyName3)
        etLocation3 = findViewById(R.id.etLocation3)
        llExpMain4 = findViewById(R.id.llExpMain4)
        etJobTitle4 = findViewById(R.id.etJobTitle4)
        etPosition4 = findViewById(R.id.etPosition4)
        etCompanyName4 = findViewById(R.id.etCompanyName4)
        etLocation4 = findViewById(R.id.etLocation4)
        llExpMain5 = findViewById(R.id.llExpMain5)
        etJobTitle5 = findViewById(R.id.etJobTitle5)
        etPosition5 = findViewById(R.id.etPosition5)
        etCompanyName5 = findViewById(R.id.etCompanyName5)
        etLocation5 = findViewById(R.id.etLocation5)
        llExpMain6 = findViewById(R.id.llExpMain6)
        etJobTitle6 = findViewById(R.id.etJobTitle6)
        etPosition6 = findViewById(R.id.etPosition6)
        etCompanyName6 = findViewById(R.id.etCompanyName6)
        etLocation6 = findViewById(R.id.etLocation6)
        llExpMain7 = findViewById(R.id.llExpMain7)
        etJobTitle7 = findViewById(R.id.etJobTitle7)
        etPosition7 = findViewById(R.id.etPosition7)
        etCompanyName7 = findViewById(R.id.etCompanyName7)
        etLocation7 = findViewById(R.id.etLocation7)
        llExpMain8 = findViewById(R.id.llExpMain8)
        etJobTitle8 = findViewById(R.id.etJobTitle8)
        etPosition8 = findViewById(R.id.etPosition8)
        etCompanyName8 = findViewById(R.id.etCompanyName8)
        etLocation8 = findViewById(R.id.etLocation8)
        llExpMain9 = findViewById(R.id.llExpMain9)
        etJobTitle9 = findViewById(R.id.etJobTitle9)
        etPosition9 = findViewById(R.id.etPosition9)
        etCompanyName9 = findViewById(R.id.etCompanyName9)
        etLocation9 = findViewById(R.id.etLocation9)
        llExpMain10 = findViewById(R.id.llExpMain10)
        etJobTitle10 = findViewById(R.id.etJobTitle10)
        etPosition10 = findViewById(R.id.etPosition10)
        etCompanyName10 = findViewById(R.id.etCompanyName10)
        etLocation10 = findViewById(R.id.etLocation10)
        skipRL = findViewById(R.id.skipRL)
        nextRL = findViewById(R.id.nextRL)
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("ExperienceData")

        downArrowExp1.visibility = View.VISIBLE

        fresher.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (fresher.isChecked) {
                fresherResult = "Yes"
                visibilityFalse()
            } else {
                llExpMain1.visibility = View.VISIBLE
                downArrowExp1.visibility = View.VISIBLE
                upArrowExp1.visibility = View.GONE
            }
        }
        llExpMain1.setOnClickListener() {
            visibilityTrue()
        }
        etLocation1.addTextChangedListener((object : TextWatcher {

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
                if (etLocation1.length() >= 5) {
                    llExpMain2.visibility = View.VISIBLE
                } else {
                    llExpMain2.visibility = View.GONE
                    llExpMain3.visibility = View.GONE
                    llExpMain4.visibility = View.GONE
                    llExpMain5.visibility = View.GONE
                    llExpMain6.visibility = View.GONE
                    llExpMain7.visibility = View.GONE
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation2.addTextChangedListener((object : TextWatcher {

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
                if (etLocation2.length() >= 5) {
                    llExpMain3.visibility = View.VISIBLE
                } else {
                    llExpMain3.visibility = View.GONE
                    llExpMain4.visibility = View.GONE
                    llExpMain5.visibility = View.GONE
                    llExpMain6.visibility = View.GONE
                    llExpMain7.visibility = View.GONE
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation3.addTextChangedListener((object : TextWatcher {

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
                if (etLocation3.length() >= 5) {
                    llExpMain4.visibility = View.VISIBLE
                } else {
                    llExpMain4.visibility = View.GONE
                    llExpMain5.visibility = View.GONE
                    llExpMain6.visibility = View.GONE
                    llExpMain7.visibility = View.GONE
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation4.addTextChangedListener((object : TextWatcher {

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
                if (etLocation4.length() >= 5) {
                    llExpMain5.visibility = View.VISIBLE
                } else {
                    llExpMain5.visibility = View.GONE
                    llExpMain6.visibility = View.GONE
                    llExpMain7.visibility = View.GONE
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation5.addTextChangedListener((object : TextWatcher {

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
                if (etLocation5.length() >= 5) {
                    llExpMain6.visibility = View.VISIBLE
                } else {
                    llExpMain6.visibility = View.GONE
                    llExpMain7.visibility = View.GONE
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation6.addTextChangedListener((object : TextWatcher {

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
                if (etLocation6.length() >= 5) {
                    llExpMain7.visibility = View.VISIBLE
                } else {
                    llExpMain7.visibility = View.GONE
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation7.addTextChangedListener((object : TextWatcher {

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
                if (etLocation7.length() >= 5) {
                    llExpMain8.visibility = View.VISIBLE
                } else {
                    llExpMain8.visibility = View.GONE
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation8.addTextChangedListener((object : TextWatcher {

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
                if (etLocation8.length() >= 5) {
                    llExpMain9.visibility = View.VISIBLE
                } else {
                    llExpMain9.visibility = View.GONE
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        etLocation9.addTextChangedListener((object : TextWatcher {

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
                if (etLocation9.length() >= 5) {
                    llExpMain10.visibility = View.VISIBLE
                } else {
                    llExpMain10.visibility = View.GONE
                }
            }
        }))
        number = intent.getStringExtra("number").toString()
        email = intent.getStringExtra("email").toString()
        skipRL.setOnClickListener() {
            val experienceIntent = Intent(this, GetReferenceInformationActivity::class.java)
            experienceIntent.putExtra("number", number)
            experienceIntent.putExtra("email", email)
            startActivity(experienceIntent)
        }
        nextRL.setOnClickListener() {
            insertData()
        }

    }

    fun insertData() {
        if (!fresher.isChecked) {
            if (etJobTitle1.text.isNotEmpty()) {
                var EDM = ExperienceDataModel(
                    etJobTitle1.text.toString(),
                    etPosition1.text.toString(),
                    etCompanyName1.text.toString(),
                    etLocation1.text.toString(),
                    etJobTitle2.text.toString(),
                    etPosition2.text.toString(),
                    etCompanyName2.text.toString(),
                    etLocation2.text.toString(),
                    etJobTitle3.text.toString(),
                    etPosition3.text.toString(),
                    etCompanyName3.text.toString(),
                    etLocation3.text.toString(),
                    etJobTitle4.text.toString(),
                    etPosition4.text.toString(),
                    etCompanyName4.text.toString(),
                    etLocation4.text.toString(),
                    etJobTitle5.text.toString(),
                    etPosition5.text.toString(),
                    etCompanyName5.text.toString(),
                    etLocation5.text.toString(),
                    etJobTitle6.text.toString(),
                    etPosition6.text.toString(),
                    etCompanyName6.text.toString(),
                    etLocation6.text.toString(),
                    etJobTitle7.text.toString(),
                    etPosition7.text.toString(),
                    etCompanyName7.text.toString(),
                    etLocation7.text.toString(),
                    etJobTitle8.text.toString(),
                    etPosition8.text.toString(),
                    etCompanyName8.text.toString(),
                    etLocation8.text.toString(),
                    etJobTitle9.text.toString(),
                    etPosition9.text.toString(),
                    etCompanyName9.text.toString(),
                    etLocation9.text.toString(),
                    etJobTitle10.text.toString(),
                    etPosition10.text.toString(),
                    etCompanyName10.text.toString(),
                    etLocation10.text.toString()
                )
                myDatabaseReference.child(number).setValue(EDM)
                    .addOnSuccessListener {
                        val referenceIntent =
                            Intent(this, GetReferenceInformationActivity::class.java)
                        referenceIntent.putExtra("number", number)
                        referenceIntent.putExtra("email", email)
                        startActivity(referenceIntent)
                    }.addOnFailureListener { Toast(this).showCustomToast("Something Wrong!", this) }
            } else {
                Toast(this).showCustomToast("Please, Fill the Value !", this)
            }
        } else {
            var EDM = ExperienceDataModel1(fresherResult)
            myDatabaseReference.child(number).setValue(EDM)
                .addOnSuccessListener {
                    val referenceIntent = Intent(this, GetReferenceInformationActivity::class.java)
                    referenceIntent.putExtra("number", number)
                    referenceIntent.putExtra("email", email)
                    startActivity(referenceIntent)
                }.addOnFailureListener { Toast(this).showCustomToast("Something Wrong!", this) }
        }

    }

    fun visibilityTrue() {
        txtExp1.setTextColor(resources.getColor(R.color.pink))
        downArrowExp1.visibility = View.GONE
        upArrowExp1.visibility = View.VISIBLE
        llExp1.visibility = View.VISIBLE
    }

    fun visibilityFalse() {
        txtExp1.setTextColor(resources.getColor(R.color.purple))
        llExpMain1.visibility = View.GONE
        llExp1.visibility = View.GONE
        llExpMain2.visibility = View.GONE
        llExpMain3.visibility = View.GONE
        llExpMain4.visibility = View.GONE
        llExpMain5.visibility = View.GONE
        llExpMain6.visibility = View.GONE
        llExpMain7.visibility = View.GONE
        llExpMain8.visibility = View.GONE
        llExpMain9.visibility = View.GONE
        llExpMain10.visibility = View.GONE
    }

}