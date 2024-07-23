package com.pmgohil.resumecenter


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GetSkillInformationActivity : AppCompatActivity() {
    lateinit var etSkillName1: EditText
    lateinit var etSkillName2: EditText
    lateinit var etSkillName3: EditText
    lateinit var etSkillName4: EditText
    lateinit var etSkillName5: EditText
    lateinit var etSkillName6: EditText
    lateinit var etSkillName7: EditText
    lateinit var etSkillName8: EditText
    lateinit var etSkillName9: EditText
    lateinit var etSkillName10: EditText
    lateinit var skipRL: RelativeLayout
    lateinit var nextRL: RelativeLayout
    lateinit var number: String
    lateinit var email: String
    lateinit var myDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_skill_information)

        etSkillName1 = findViewById(R.id.etSkillName1)
        etSkillName2 = findViewById(R.id.etSkillName2)
        etSkillName3 = findViewById(R.id.etSkillName3)
        etSkillName4 = findViewById(R.id.etSkillName4)
        etSkillName5 = findViewById(R.id.etSkillName5)
        etSkillName6 = findViewById(R.id.etSkillName6)
        etSkillName7 = findViewById(R.id.etSkillName7)
        etSkillName8 = findViewById(R.id.etSkillName8)
        etSkillName9 = findViewById(R.id.etSkillName9)
        etSkillName10 = findViewById(R.id.etSkillName10)
        skipRL = findViewById(R.id.skipRL)
        nextRL = findViewById(R.id.nextRL)
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("SkillData")

        etSkillName1.addTextChangedListener((object : TextWatcher {

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
                //etSkillName1.setText(s)
                if (etSkillName1.length() >= 4) {
                    etSkillName2.visibility = View.VISIBLE
                } else {
                    etSkillName2.visibility = View.GONE
                }
            }
        }))
        etSkillName2.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName2.length() >= 4) {
                    etSkillName3.visibility = View.VISIBLE
                } else {
                    etSkillName3.visibility = View.GONE
                }
            }
        }))
        etSkillName3.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName3.length() >= 4) {
                    etSkillName4.visibility = View.VISIBLE
                } else {
                    etSkillName4.visibility = View.GONE
                }
            }
        }))
        etSkillName4.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName4.length() >= 4) {
                    etSkillName5.visibility = View.VISIBLE
                } else {
                    etSkillName5.visibility = View.GONE
                }
            }
        }))
        etSkillName5.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName5.length() >= 4) {
                    etSkillName6.visibility = View.VISIBLE
                } else {
                    etSkillName6.visibility = View.GONE
                }
            }
        }))
        etSkillName6.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName6.length() > 4) {
                    etSkillName7.visibility = View.VISIBLE
                } else {
                    etSkillName7.visibility = View.GONE
                }
            }
        }))
        etSkillName7.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName7.length() >= 4) {
                    etSkillName8.visibility = View.VISIBLE
                } else {
                    etSkillName8.visibility = View.GONE
                }
            }
        }))
        etSkillName8.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName8.length() >= 4) {
                    etSkillName9.visibility = View.VISIBLE
                } else {
                    etSkillName9.visibility = View.GONE
                }
            }
        }))
        etSkillName9.addTextChangedListener((object : TextWatcher {

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
                if (etSkillName9.length() >= 4) {
                    etSkillName10.visibility = View.VISIBLE
                } else {
                    etSkillName10.visibility = View.GONE
                }
            }
        }))
        number = intent.getStringExtra("number").toString()
        email = intent.getStringExtra("email").toString()
        skipRL.setOnClickListener() {
            val skillIntent = Intent(this, GetExperienceInfoActivity::class.java)
            skillIntent.putExtra("number", number)
            skillIntent.putExtra("email", email)
            startActivity(skillIntent)
        }
        nextRL.setOnClickListener() {
            insertData()
        }

    }

    fun insertData() {
        if (etSkillName1.text.isNotEmpty()) {
            var SDM = SkillDataModel(
                etSkillName1.text.toString(),
                etSkillName2.text.toString(),
                etSkillName3.text.toString(),
                etSkillName4.text.toString(),
                etSkillName5.text.toString(),
                etSkillName6.text.toString(),
                etSkillName7.text.toString(),
                etSkillName8.text.toString(),
                etSkillName9.text.toString(),
                etSkillName10.text.toString()
            )
            myDatabaseReference.child(number).setValue(SDM)
                .addOnSuccessListener {
                    val skillIntent = Intent(this, GetExperienceInfoActivity::class.java)
                    skillIntent.putExtra("number", number)
                    skillIntent.putExtra("email", email)
                    startActivity(skillIntent)
                }.addOnFailureListener { Toast(this).showCustomToast("Something Wrong!", this) }
        } else {
            Toast(this).showCustomToast("Please, Fill the Value !", this)
        }

    }
}