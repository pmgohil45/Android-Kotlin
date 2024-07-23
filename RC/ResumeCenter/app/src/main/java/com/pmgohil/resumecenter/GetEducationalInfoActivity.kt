package com.pmgohil.resumecenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GetEducationalInfoActivity : AppCompatActivity() {

    lateinit var myDatabaseReference: DatabaseReference
    lateinit var ll10std: LinearLayout
    lateinit var txt10thStandard: TextView
    lateinit var upArrow10: ImageView
    lateinit var downArrow10: ImageView
    lateinit var schoolName10: EditText
    lateinit var schoolAddress10: EditText
    lateinit var contact10: EditText
    lateinit var degreeName10: EditText
    lateinit var percentage10: EditText
    lateinit var passingYear10: EditText
    lateinit var ll12std: LinearLayout
    lateinit var txt12thStandard: TextView
    lateinit var upArrow12: ImageView
    lateinit var downArrow12: ImageView
    lateinit var schoolName12: EditText
    lateinit var schoolAddress12: EditText
    lateinit var contact12: EditText
    lateinit var degreeName12: EditText
    lateinit var txtStream: TextView
    lateinit var passingYear12: EditText
    lateinit var streamRadio: RadioGroup
    lateinit var artsRadio: RadioButton
    lateinit var commerceRadio: RadioButton
    lateinit var scienceRadio: RadioButton
    lateinit var percentage12: EditText
    lateinit var llGraduation: LinearLayout
    lateinit var txtGraduation: TextView
    lateinit var downArrowGraduation: ImageView
    lateinit var upArrowGraduation: ImageView
    lateinit var universityNameGraduation: EditText
    lateinit var collegeNameGraduation: EditText
    lateinit var collegeAddressGraduation: EditText
    lateinit var contactGraduation: EditText
    lateinit var degreeNameGraduation: EditText
    lateinit var percentageGraduation: EditText
    lateinit var passingYearGraduation: EditText
    lateinit var llPostGraduation: LinearLayout
    lateinit var txtPostGraduation: TextView
    lateinit var downArrowPostGraduation: ImageView
    lateinit var upArrowPostGraduation: ImageView
    lateinit var universityNamePostGraduation: EditText
    lateinit var collegeNamePostGraduation: EditText
    lateinit var collegeAddressPostGraduation: EditText
    lateinit var contactPostGraduation: EditText
    lateinit var degreeNamePostGraduation: EditText
    lateinit var percentagePostGraduation: EditText
    lateinit var passingYearPostGraduation: EditText
    lateinit var llOtherDegree1: LinearLayout
    lateinit var txtOtherDegree1: TextView
    lateinit var downArrowOtherDegree1: ImageView
    lateinit var upArrowOtherDegree1: ImageView
    lateinit var otherDegreeName1: EditText
    lateinit var otherDegreePercentage1: EditText
    lateinit var otherDegreePassingYear1: EditText
    lateinit var skipRL: RelativeLayout
    lateinit var nextRL: RelativeLayout
    lateinit var llOneMore: LinearLayout
    lateinit var txtOneMore: TextView
    lateinit var llOtherDegree2: LinearLayout
    lateinit var txtOtherDegree2: TextView
    lateinit var downArrowOtherDegree2: ImageView
    lateinit var upArrowOtherDegree2: ImageView
    lateinit var otherDegreeName2: EditText
    lateinit var otherDegreePercentage2: EditText
    lateinit var otherDegreePassingYear2: EditText
    lateinit var num: String
    lateinit var email: String
    lateinit var streamResult: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_educational_info)

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("EducationData")

        ll10std = findViewById(R.id.ll10std)
        txt10thStandard = findViewById(R.id.txt10thStandard)
        upArrow10 = findViewById(R.id.upArrow10)
        downArrow10 = findViewById(R.id.downArrow10)
        schoolName10 = findViewById(R.id.etSchoolName10)
        schoolAddress10 = findViewById(R.id.etSchoolAddress10)
        contact10 = findViewById(R.id.etContact10)
        degreeName10 = findViewById(R.id.etDegreeName10)
        percentage10 = findViewById(R.id.etPercentage10)
        passingYear10 = findViewById(R.id.etPassingYear10)
        ll12std = findViewById(R.id.ll12std)
        txt12thStandard = findViewById(R.id.txt12thStandard)
        upArrow12 = findViewById(R.id.upArrow12)
        downArrow12 = findViewById(R.id.downArrow12)
        schoolName12 = findViewById(R.id.etSchoolName12)
        schoolAddress12 = findViewById(R.id.etSchoolAddress12)
        contact12 = findViewById(R.id.etContact12)
        degreeName12 = findViewById(R.id.etDegreeName12)
        txtStream = findViewById(R.id.txtStream)
        passingYear12 = findViewById(R.id.etPassingYear12)
        streamRadio = findViewById(R.id.streamRadio)
        artsRadio = findViewById(R.id.arts)
        commerceRadio = findViewById(R.id.commerce)
        scienceRadio = findViewById(R.id.science)
        percentage12 = findViewById(R.id.etPercentage12)
        llGraduation = findViewById(R.id.llGraduation)
        txtGraduation = findViewById(R.id.txtGraduation)
        downArrowGraduation = findViewById(R.id.downArrowGraduation)
        upArrowGraduation = findViewById(R.id.upArrowGraduation)
        universityNameGraduation = findViewById(R.id.etUniversityNameGraduation)
        collegeNameGraduation = findViewById(R.id.etCollegeNameGraduation)
        collegeAddressGraduation = findViewById(R.id.etCollegeAddressGraduation)
        contactGraduation = findViewById(R.id.etContactGraduation)
        degreeNameGraduation = findViewById(R.id.etDegreeNameGraduation)
        percentageGraduation = findViewById(R.id.etPercentageGraduation)
        passingYearGraduation = findViewById(R.id.etPassingYearGraduation)
        llPostGraduation = findViewById(R.id.llPostGraduation)
        txtPostGraduation = findViewById(R.id.txtPostGraduation)
        downArrowPostGraduation = findViewById(R.id.downArrowPostGraduation)
        upArrowPostGraduation = findViewById(R.id.upArrowPostGraduation)
        universityNamePostGraduation = findViewById(R.id.etUniversityNamePostGraduation)
        collegeNamePostGraduation = findViewById(R.id.etCollegeNamePostGraduation)
        collegeAddressPostGraduation = findViewById(R.id.etCollegeAddressPostGraduation)
        contactPostGraduation = findViewById(R.id.etContactPostGraduation)
        degreeNamePostGraduation = findViewById(R.id.etDegreeNamePostGraduation)
        percentagePostGraduation = findViewById(R.id.etPercentagePostGraduation)
        passingYearPostGraduation = findViewById(R.id.etPassingYearPostGraduation)
        llOtherDegree1 = findViewById(R.id.llOtherDegree1)
        txtOtherDegree1 = findViewById(R.id.txtOtherDegree1)
        downArrowOtherDegree1 = findViewById(R.id.downArrowOtherDegree1)
        upArrowOtherDegree1 = findViewById(R.id.upArrowOtherDegree1)
        otherDegreeName1 = findViewById(R.id.etDegreeNameOtherDegree1)
        otherDegreePercentage1 = findViewById(R.id.etPercentageOtherDegree1)
        otherDegreePassingYear1 = findViewById(R.id.etPassingYearOtherDegree1)
        skipRL = findViewById(R.id.skipRL)
        nextRL = findViewById(R.id.nextRL)
        llOneMore = findViewById(R.id.llOneMore)
        txtOneMore = findViewById(R.id.txtOneMore)
        llOtherDegree2 = findViewById(R.id.llOtherDegree2)
        txtOtherDegree2 = findViewById(R.id.txtOtherDegree2)
        downArrowOtherDegree2 = findViewById(R.id.downArrowOtherDegree2)
        upArrowOtherDegree2 = findViewById(R.id.upArrowOtherDegree2)
        otherDegreeName2 = findViewById(R.id.etDegreeNameOtherDegree2)
        otherDegreePercentage2 = findViewById(R.id.etPercentageOtherDegree2)
        otherDegreePassingYear2 = findViewById(R.id.etPassingYearOtherDegree2)

        downArrow10.visibility = View.VISIBLE
        downArrow12.visibility = View.VISIBLE
        downArrowGraduation.visibility = View.VISIBLE
        downArrowPostGraduation.visibility = View.VISIBLE
        downArrowOtherDegree1.visibility = View.VISIBLE


        ll10std.setOnClickListener() {
            standard10VisibilityTrue()
            standard12VisibilityFalse()
            graduationVisibilityFalse()
            postGraduationVisibilityFalse()
            otherDegreeVisibilityFalse()
            otherDegree2VisibilityFalse()
        }
        ll12std.setOnClickListener() {
            standard10VisibilityFalse()
            standard12VisibilityTrue()
            graduationVisibilityFalse()
            postGraduationVisibilityFalse()
            otherDegreeVisibilityFalse()
            otherDegree2VisibilityFalse()
        }
        llGraduation.setOnClickListener() {
            standard10VisibilityFalse()
            standard12VisibilityFalse()
            graduationVisibilityTrue()
            postGraduationVisibilityFalse()
            otherDegreeVisibilityFalse()
            otherDegree2VisibilityFalse()
        }
        llPostGraduation.setOnClickListener() {
            standard10VisibilityFalse()
            standard12VisibilityFalse()
            graduationVisibilityFalse()
            postGraduationVisibilityTrue()
            otherDegreeVisibilityFalse()
            otherDegree2VisibilityFalse()
        }
        llOtherDegree1.setOnClickListener() {
            standard10VisibilityFalse()
            standard12VisibilityFalse()
            graduationVisibilityFalse()
            postGraduationVisibilityFalse()
            otherDegreeVisibilityTrue()
            otherDegree2VisibilityFalse()
        }
        txtOneMore.setOnClickListener() {
            standard10VisibilityFalse()
            standard12VisibilityFalse()
            graduationVisibilityFalse()
            postGraduationVisibilityFalse()
            otherDegreeVisibilityFalse()
            otherDegree2VisibilityTrue()
        }
        skipRL.setOnClickListener() {
            val intentEducation = Intent(this, GetSkillInformationActivity::class.java)
            intentEducation.putExtra("number", num)
            intentEducation.putExtra("email", email)
            startActivity(intentEducation)
        }
        num = intent.getStringExtra("number").toString()
        email = intent.getStringExtra("email").toString()
        nextRL.setOnClickListener() {
            getStreamValue()
            insertData()
        }
    }

    fun insertData() {
        if (schoolName10.text.isNotEmpty()) {
            val EDM = EducationDataModel(
                schoolName10.text.toString(),
                schoolAddress10.text.toString(),
                contact10.text.toString(),
                degreeName10.text.toString(),
                percentage10.text.toString(),
                passingYear10.text.toString(),
                schoolName12.text.toString(),
                schoolAddress12.text.toString(),
                contact12.text.toString(),
                streamResult,
                degreeName12.text.toString(),
                passingYear12.text.toString(),
                percentage12.text.toString(),
                universityNameGraduation.text.toString(),
                collegeNameGraduation.text.toString(),
                collegeAddressGraduation.text.toString(),
                contactGraduation.text.toString(),
                degreeNameGraduation.text.toString(),
                percentageGraduation.text.toString(),
                passingYearGraduation.text.toString(),
                universityNamePostGraduation.text.toString(),
                collegeNamePostGraduation.text.toString(),
                collegeAddressPostGraduation.text.toString(),
                contactPostGraduation.text.toString(),
                degreeNamePostGraduation.text.toString(),
                percentagePostGraduation.text.toString(),
                passingYearPostGraduation.text.toString(),
                otherDegreeName1.text.toString(),
                otherDegreePercentage1.text.toString(),
                otherDegreePassingYear1.text.toString(),
                otherDegreeName2.text.toString(),
                otherDegreePercentage2.text.toString(),
                otherDegreePassingYear2.text.toString()

            )
            myDatabaseReference.child(num).setValue(EDM)
                .addOnSuccessListener {
                    val intentEducation = Intent(this, GetSkillInformationActivity::class.java)
                    intentEducation.putExtra("number", num)
                    intentEducation.putExtra("email", email)
                    startActivity(intentEducation)
                }.addOnFailureListener { Toast(this).showCustomToast("Something Wrong!", this) }
        } else {
            Toast(this).showCustomToast("Please, Fill The Value !", this)
        }
    }

    fun getStreamValue() {
        val id: Int = streamRadio.checkedRadioButtonId
        if (id >= 1) {
            val radio: RadioButton = findViewById(id)
            streamResult = "${radio.text}"
            //Toast(this).showCustomToast(streamResult, this)
        } else {
            streamResult = ""
        }
    }
    // visibility true or false functions

    fun standard10VisibilityTrue() {
        txt10thStandard.setTextColor(resources.getColor(R.color.pink))
        downArrow10.visibility = View.GONE
        upArrow10.visibility = View.VISIBLE
        schoolName10.visibility = View.VISIBLE
        schoolAddress10.visibility = View.VISIBLE
        contact10.visibility = View.VISIBLE
        degreeName10.visibility = View.VISIBLE
        percentage10.visibility = View.VISIBLE
        passingYear10.visibility = View.VISIBLE
    }

    fun standard10VisibilityFalse() {
        txt10thStandard.setTextColor(resources.getColor(R.color.purple))
        downArrow10.visibility = View.VISIBLE
        upArrow10.visibility = View.GONE
        schoolName10.visibility = View.GONE
        schoolAddress10.visibility = View.GONE
        contact10.visibility = View.GONE
        degreeName10.visibility = View.GONE
        percentage10.visibility = View.GONE
        passingYear10.visibility = View.GONE
    }

    fun standard12VisibilityTrue() {
        txt12thStandard.setTextColor(resources.getColor(R.color.pink))
        downArrow12.visibility = View.GONE
        upArrow12.visibility = View.VISIBLE
        schoolName12.visibility = View.VISIBLE
        schoolAddress12.visibility = View.VISIBLE
        contact12.visibility = View.VISIBLE
        txtStream.visibility = View.VISIBLE
        streamRadio.visibility = View.VISIBLE
        degreeName12.visibility = View.VISIBLE
        percentage12.visibility = View.VISIBLE
        passingYear12.visibility = View.VISIBLE
    }

    fun standard12VisibilityFalse() {
        txt12thStandard.setTextColor(resources.getColor(R.color.purple))
        downArrow12.visibility = View.VISIBLE
        upArrow12.visibility = View.GONE
        schoolName12.visibility = View.GONE
        schoolAddress12.visibility = View.GONE
        contact12.visibility = View.GONE
        txtStream.visibility = View.GONE
        streamRadio.visibility = View.GONE
        degreeName12.visibility = View.GONE
        percentage12.visibility = View.GONE
        passingYear12.visibility = View.GONE
    }

    fun graduationVisibilityTrue() {
        txtGraduation.setTextColor(resources.getColor(R.color.pink))
        downArrowGraduation.visibility = View.GONE
        upArrowGraduation.visibility = View.VISIBLE
        universityNameGraduation.visibility = View.VISIBLE
        collegeNameGraduation.visibility = View.VISIBLE
        collegeAddressGraduation.visibility = View.VISIBLE
        contactGraduation.visibility = View.VISIBLE
        degreeNameGraduation.visibility = View.VISIBLE
        percentageGraduation.visibility = View.VISIBLE
        passingYearGraduation.visibility = View.VISIBLE
    }

    fun graduationVisibilityFalse() {
        txtGraduation.setTextColor(resources.getColor(R.color.purple))
        downArrowGraduation.visibility = View.VISIBLE
        upArrowGraduation.visibility = View.GONE
        universityNameGraduation.visibility = View.GONE
        collegeNameGraduation.visibility = View.GONE
        collegeAddressGraduation.visibility = View.GONE
        contactGraduation.visibility = View.GONE
        degreeNameGraduation.visibility = View.GONE
        percentageGraduation.visibility = View.GONE
        passingYearGraduation.visibility = View.GONE
    }

    fun postGraduationVisibilityTrue() {
        txtPostGraduation.setTextColor(resources.getColor(R.color.pink))
        downArrowPostGraduation.visibility = View.GONE
        upArrowPostGraduation.visibility = View.VISIBLE
        universityNamePostGraduation.visibility = View.VISIBLE
        collegeNamePostGraduation.visibility = View.VISIBLE
        collegeAddressPostGraduation.visibility = View.VISIBLE
        contactPostGraduation.visibility = View.VISIBLE
        degreeNamePostGraduation.visibility = View.VISIBLE
        percentagePostGraduation.visibility = View.VISIBLE
        passingYearPostGraduation.visibility = View.VISIBLE
    }

    fun postGraduationVisibilityFalse() {
        txtPostGraduation.setTextColor(resources.getColor(R.color.purple))
        downArrowPostGraduation.visibility = View.VISIBLE
        upArrowPostGraduation.visibility = View.GONE
        universityNamePostGraduation.visibility = View.GONE
        collegeNamePostGraduation.visibility = View.GONE
        collegeAddressPostGraduation.visibility = View.GONE
        contactPostGraduation.visibility = View.GONE
        degreeNamePostGraduation.visibility = View.GONE
        percentagePostGraduation.visibility = View.GONE
        passingYearPostGraduation.visibility = View.GONE
    }

    fun otherDegreeVisibilityTrue() {
        txtOtherDegree1.setTextColor(resources.getColor(R.color.pink))
        downArrowOtherDegree1.visibility = View.GONE
        upArrowOtherDegree1.visibility = View.VISIBLE
        otherDegreeName1.visibility = View.VISIBLE
        otherDegreePercentage1.visibility = View.VISIBLE
        otherDegreePassingYear1.visibility = View.VISIBLE
    }

    fun otherDegreeVisibilityFalse() {
        txtOtherDegree1.setTextColor(resources.getColor(R.color.purple))
        downArrowOtherDegree1.visibility = View.VISIBLE
        upArrowOtherDegree1.visibility = View.GONE
        otherDegreeName1.visibility = View.GONE
        otherDegreePercentage1.visibility = View.GONE
        otherDegreePassingYear1.visibility = View.GONE
    }

    fun otherDegree2VisibilityTrue() {
        txtOtherDegree1.text = "Detail of Other Degree 1\n(If Applicable)"
        txtOtherDegree2.setTextColor(resources.getColor(R.color.pink))
        llOtherDegree2.visibility = View.VISIBLE
        downArrowOtherDegree2.visibility = View.GONE
        upArrowOtherDegree2.visibility = View.VISIBLE
        otherDegreeName2.visibility = View.VISIBLE
        otherDegreePercentage2.visibility = View.VISIBLE
        otherDegreePassingYear2.visibility = View.VISIBLE
    }

    fun otherDegree2VisibilityFalse() {
        txtOtherDegree2.setTextColor(resources.getColor(R.color.purple))
        llOtherDegree2.visibility = View.GONE
        downArrowOtherDegree2.visibility = View.VISIBLE
        upArrowOtherDegree2.visibility = View.GONE
        otherDegreeName2.visibility = View.GONE
        otherDegreePercentage2.visibility = View.GONE
        otherDegreePassingYear2.visibility = View.GONE
    }
}