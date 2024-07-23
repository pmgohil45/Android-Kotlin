package com.pmgohil.resumecenter

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.*
import android.print.PrintManager
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.*
import java.util.*


class TemplateActivity : AppCompatActivity() {

    lateinit var templateRL1: RelativeLayout
    lateinit var templateRL2: RelativeLayout
    lateinit var templateRL3: RelativeLayout

    /*lateinit var templateRL4: RelativeLayout
    lateinit var templateRL5: RelativeLayout
    lateinit var templateRL6: RelativeLayout
    lateinit var templateRL7: RelativeLayout
    lateinit var templateRL8: RelativeLayout
    lateinit var templateRL9: RelativeLayout
    lateinit var templateRL10: RelativeLayout
    */
    lateinit var myPersonalDR: DatabaseReference
    lateinit var myEducationDR: DatabaseReference
    lateinit var mySkillDR: DatabaseReference
    lateinit var myExperienceDR: DatabaseReference
    lateinit var myReferenceDR: DatabaseReference

    lateinit var number: String
    lateinit var email: String
    var PERMISSION_CODE = 101

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)

        templateRL1 = findViewById(R.id.templateRL1)
        templateRL2 = findViewById(R.id.templateRL2)
        templateRL3 = findViewById(R.id.templateRL3)
        /*templateRL4 = findViewById(R.id.templateRL4)
        templateRL5 = findViewById(R.id.templateRL5)
        templateRL6 = findViewById(R.id.templateRL6)
        templateRL7 = findViewById(R.id.templateRL7)
        templateRL8 = findViewById(R.id.templateRL8)
        templateRL9 = findViewById(R.id.templateRL9)
        templateRL10 = findViewById(R.id.templateRL10)*/

        myPersonalDR = FirebaseDatabase.getInstance().getReference("RegistrationData")
        myEducationDR = FirebaseDatabase.getInstance().getReference("EducationData")
        mySkillDR = FirebaseDatabase.getInstance().getReference("SkillData")
        myExperienceDR = FirebaseDatabase.getInstance().getReference("ExperienceData")
        myReferenceDR = FirebaseDatabase.getInstance().getReference("ReferenceData")

        number = intent.getStringExtra("number").toString()
        email = intent.getStringExtra("email").toString()
        //number = "9512240793"//for the check

        templateRL1.setOnClickListener() {
            setContentView(R.layout.activity_basic_template)
            fullScreenMode()
            templateDownloadAsSS()
            selectTemplateData1()
        }

        templateRL2.setOnClickListener() {
            setContentView(R.layout.activity_tabular_template)
            fullScreenMode()
            templateDownloadAsSS()
            selectTemplateData2()
        }

        templateRL3.setOnClickListener() {
            setContentView(R.layout.activity_rc_template)
            fullScreenMode()
            templateDownloadAsSS()
            selectTemplateData3()
        }


        /*
        templateRL4.setOnClickListener() {}
        templateRL5.setOnClickListener() {}
        templateRL6.setOnClickListener() {}
        templateRL7.setOnClickListener() {}
        templateRL8.setOnClickListener() {}
        templateRL9.setOnClickListener() {}
        templateRL10.setOnClickListener() {}
        */

    }

    private fun fullScreenMode() {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        supportActionBar

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun templateDownloadAsSS() {

        val backArrow: ImageView = findViewById(R.id.backArrowImg)
        backArrow.setOnClickListener() {
            finish()
            val intent = Intent(this, TemplateActivity::class.java)
            intent.putExtra("number", number)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        if (!checkPermissions()) {
            requestPermission()
        }

        val download: ImageView = findViewById(R.id.downloadImg)
        download.setOnClickListener() {
            val basicTempScrollView = findViewById<ScrollView>(R.id.basicTempScrollView)
            try {
                val bitmap: Bitmap = getBitmapFromView(
                    basicTempScrollView,
                    basicTempScrollView.getChildAt(0).getHeight(),
                    basicTempScrollView.getChildAt(0).getWidth()
                )!!

                //val bitmap = getSS(basicTempScrollView)
                if (bitmap != null) {
                    saveSSToStorage(bitmap)
                }
            } catch (e: java.lang.Exception) {
                Toast(this).showCustomToast("$e", this)
            }

        }
    }

    //create bitmap from the ScrollView
    private fun getBitmapFromView(view: View, height: Int, width: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }

    fun saveSSToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast(this).showCustomToast("Captured View and saved to Gallery", this)
        }
    }

    fun checkPermissions(): Boolean {
        val writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_CODE
        )
    }

    fun selectTemplateData1() {

        val txtName: TextView = findViewById(R.id.txtName)
        val txtAddress: TextView = findViewById(R.id.txtAddress)
        val txtPhone: TextView = findViewById(R.id.txtPhone)
        val txtEmail: TextView = findViewById(R.id.txtEmail)
        val txtEducation: TextView = findViewById(R.id.txtEducation)
        val llEducation: LinearLayout = findViewById(R.id.llEducation)
        val txtPassingYear10: TextView = findViewById(R.id.txtPassingYear10)
        val txtSchoolName10: TextView = findViewById(R.id.txtSchoolName10)
        val txtSchoolDegree10: TextView = findViewById(R.id.txtSchoolDegree10)
        val txtPercentage10: TextView = findViewById(R.id.txtPercentage10)
        val txtPassingYear12: TextView = findViewById(R.id.txtPassingYear12)
        val txtSchoolName12: TextView = findViewById(R.id.txtSchoolName12)
        val txtSchoolDegree12: TextView = findViewById(R.id.txtSchoolDegree12)
        val txtPercentage12: TextView = findViewById(R.id.txtPercentage12)
        val txtPassingYearUG: TextView = findViewById(R.id.txtPassingYearUG)
        val txtUniversityNameUG: TextView = findViewById(R.id.txtUniversityNameUG)
        val txtUniversityDegreeUG: TextView = findViewById(R.id.txtUniversityDegreeUG)
        val txtPercentageUG: TextView = findViewById(R.id.txtPercentageUG)
        val txtPassingYearPG: TextView = findViewById(R.id.txtPassingYearPG)
        val txtUniversityNamePG: TextView = findViewById(R.id.txtUniversityNamePG)
        val txtUniversityDegreePG: TextView = findViewById(R.id.txtUniversityDegreePG)
        val txtPercentagePG: TextView = findViewById(R.id.txtPercentagePG)
        val txtSkill: TextView = findViewById(R.id.txtSkill)
        val rlSkill: RelativeLayout = findViewById(R.id.rlSkill)
        val txtSkill1: TextView = findViewById(R.id.txtSkill1)
        val txtSkill2: TextView = findViewById(R.id.txtSkill2)
        val txtSkill3: TextView = findViewById(R.id.txtSkill3)
        val txtExp: TextView = findViewById(R.id.txtExp)
        val txtFresher: TextView = findViewById(R.id.txtFresher)
        val txtExpNum1: TextView = findViewById(R.id.txtExpNum1)
        val llTxtExp1: LinearLayout = findViewById(R.id.llTxtExp1)
        val txtCompanyName1: TextView = findViewById(R.id.txtCompanyName1)
        val txtPosition1: TextView = findViewById(R.id.txtPosition1)
        val txtLocation1: TextView = findViewById(R.id.txtLocation1)
        val llTxtExp2: LinearLayout = findViewById(R.id.llTxtExp2)
        val txtExpNum2: TextView = findViewById(R.id.txtExpNum2)
        val txtCompanyName2: TextView = findViewById(R.id.txtCompanyName2)
        val txtPosition2: TextView = findViewById(R.id.txtPosition2)
        val txtLocation2: TextView = findViewById(R.id.txtLocation2)
        val txtReference: TextView = findViewById(R.id.txtReference)
        val rlReference: RelativeLayout = findViewById(R.id.rlReference)
        val llTxtReference1: LinearLayout = findViewById(R.id.llTxtReference1)
        val txtReferenceNum1: TextView = findViewById(R.id.txtReferenceNum1)
        val txtReferenceName1: TextView = findViewById(R.id.txtReferenceName1)
        val txtReferenceCompanyName1: TextView = findViewById(R.id.txtReferenceCompanyName1)
        val txtReferencePosition1: TextView = findViewById(R.id.txtReferencePosition1)
        val txtReferencePhone1: TextView = findViewById(R.id.txtReferencePhone1)
        val llTxtReference2: LinearLayout = findViewById(R.id.llTxtReference2)
        val txtReferenceNum2: TextView = findViewById(R.id.txtReferenceNum2)
        val txtReferenceName2: TextView = findViewById(R.id.txtReferenceName2)
        val txtReferenceCompanyName2: TextView = findViewById(R.id.txtReferenceCompanyName2)
        val txtReferencePosition2: TextView = findViewById(R.id.txtReferencePosition2)
        val txtReferencePhone2: TextView = findViewById(R.id.txtReferencePhone2)

        if (number.isNotEmpty()) {
            myPersonalDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    iamgeFatch()
                    txtName.text = it.child("name").value.toString()
                    txtAddress.text = it.child("address").value.toString()
                    txtPhone.text = it.child("number").value.toString()
                    txtEmail.text = it.child("email").value.toString()
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myEducationDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val passY10 = it.child("passingYear10").value.toString()
                    val passY12 = it.child("passingYear12").value.toString()
                    val passUG = it.child("passingYearGraduation").value.toString()
                    val passPG = it.child("passingYearPostGraduation").value.toString()
                    if (passY10 != "" && passY12 != "" && passUG != "" && passPG != "") {
                        txtPassingYear10.text = passY10
                        txtSchoolName10.text = it.child("schoolName10").value.toString()
                        txtSchoolDegree10.text = it.child("degreeName10").value.toString()
                        txtPercentage10.text = it.child("percentage10").value.toString()
                        txtPassingYear12.text = passY12
                        txtSchoolName12.text = it.child("schoolName12").value.toString()
                        txtSchoolDegree12.text = it.child("degreeName12").value.toString()
                        txtPercentage12.text = it.child("percentage12").value.toString()
                        txtPassingYearUG.text = passUG
                        txtUniversityNameUG.text =
                            it.child("universityNameGraduation").value.toString()
                        txtUniversityDegreeUG.text =
                            it.child("degreeNameGraduation").value.toString()
                        txtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        txtPassingYearPG.text = passPG
                        txtUniversityNamePG.text =
                            it.child("universityNamePostGraduation").value.toString()
                        txtUniversityDegreePG.text =
                            it.child("degreeNamePostGraduation").value.toString()
                        txtPercentagePG.text = it.child("percentagePostGraduation").value.toString()
                        txtEducation.visibility = View.VISIBLE
                        llEducation.visibility = View.VISIBLE
                    } else if (passY10 != "" && passY12 != "" && passUG != "") {
                        txtPassingYear10.text = passY10
                        txtSchoolName10.text = it.child("schoolName10").value.toString()
                        txtSchoolDegree10.text = it.child("degreeName10").value.toString()
                        txtPercentage10.text = it.child("percentage10").value.toString()
                        txtPassingYear12.text = passY12
                        txtSchoolName12.text = it.child("schoolName12").value.toString()
                        txtSchoolDegree12.text = it.child("degreeName12").value.toString()
                        txtPercentage12.text = it.child("percentage12").value.toString()
                        txtPassingYearUG.text = passUG
                        txtUniversityNameUG.text =
                            it.child("universityNameGraduation").value.toString()
                        txtUniversityDegreeUG.text =
                            it.child("degreeNameGraduation").value.toString()
                        txtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        txtPassingYearPG.visibility = View.GONE
                        txtUniversityNamePG.visibility = View.GONE
                        txtUniversityDegreePG.visibility = View.GONE
                        txtPercentagePG.visibility = View.GONE
                        txtEducation.visibility = View.VISIBLE
                        llEducation.visibility = View.VISIBLE
                    } else if (passY10 != "" && passY12 != "") {
                        txtPassingYear10.text = passY10
                        txtSchoolName10.text = it.child("schoolName10").value.toString()
                        txtSchoolDegree10.text = it.child("degreeName10").value.toString()
                        txtPercentage10.text = it.child("percentage10").value.toString()
                        txtPassingYear12.text = passY12
                        txtSchoolName12.text = it.child("schoolName12").value.toString()
                        txtSchoolDegree12.text = it.child("degreeName12").value.toString()
                        txtPercentage12.text = it.child("percentage12").value.toString()
                        txtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        txtPassingYearUG.visibility = View.GONE
                        txtUniversityNameUG.visibility = View.GONE
                        txtUniversityDegreeUG.visibility = View.GONE
                        txtPercentageUG.visibility = View.GONE
                        txtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        txtPassingYearPG.visibility = View.GONE
                        txtUniversityNamePG.visibility = View.GONE
                        txtUniversityDegreePG.visibility = View.GONE
                        txtPercentagePG.visibility = View.GONE
                        txtEducation.visibility = View.VISIBLE
                        llEducation.visibility = View.VISIBLE
                    } else if (passY10 != "") {
                        txtPassingYear10.text = passY10
                        txtSchoolName10.text = it.child("schoolName10").value.toString()
                        txtSchoolDegree10.text = it.child("degreeName10").value.toString()
                        txtPercentage10.text = it.child("percentage10").value.toString()
                        txtPassingYear12.visibility = View.GONE
                        txtSchoolName12.visibility = View.GONE
                        txtSchoolDegree12.visibility = View.GONE
                        txtPercentage12.visibility = View.GONE
                        txtPassingYearUG.visibility = View.GONE
                        txtUniversityNameUG.visibility = View.GONE
                        txtUniversityDegreeUG.visibility = View.GONE
                        txtPercentageUG.visibility = View.GONE
                        txtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        txtPassingYearPG.visibility = View.GONE
                        txtUniversityNamePG.visibility = View.GONE
                        txtUniversityDegreePG.visibility = View.GONE
                        txtPercentagePG.visibility = View.GONE
                        txtEducation.visibility = View.VISIBLE
                        llEducation.visibility = View.VISIBLE
                    } else {
                        txtEducation.visibility = View.GONE
                        llEducation.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            mySkillDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val s1 = it.child("skillName1").value.toString()
                    val s2 = it.child("skillName2").value.toString()
                    val s3 = it.child("skillName3").value.toString()
                    if (s1 != "" && s2 != "" && s3 != "") {
                        txtSkill1.text = s1
                        txtSkill2.text = s2
                        txtSkill3.text = s3
                        txtSkill.visibility = View.VISIBLE
                        rlSkill.visibility = View.VISIBLE
                    } else if (s1 != "" && s2 != "") {
                        txtSkill1.text = s1
                        txtSkill2.text = s2
                        txtSkill3.visibility = View.GONE
                        txtSkill.visibility = View.VISIBLE
                        rlSkill.visibility = View.VISIBLE
                    } else if (s1 != "") {
                        txtSkill1.text = s1
                        txtSkill2.visibility = View.GONE
                        txtSkill3.visibility = View.GONE
                        txtSkill.visibility = View.VISIBLE
                        rlSkill.visibility = View.VISIBLE
                    } else {
                        txtSkill.visibility = View.GONE
                        rlSkill.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myExperienceDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val f = it.child("fresher").value.toString()
                    val cn1 = it.child("companyName1").value.toString()
                    val cn2 = it.child("companyName2").value.toString()
                    if (f == "Yes") {
                        txtExp.visibility = View.VISIBLE
                        txtFresher.visibility = View.VISIBLE
                        txtExpNum1.visibility = View.GONE
                        llTxtExp1.visibility = View.GONE
                        txtExpNum2.visibility = View.GONE
                        llTxtExp2.visibility = View.GONE
                    } else if (cn1 != "" && cn2 != "") {
                        txtCompanyName1.text = cn1
                        txtPosition1.text = it.child("position1").value.toString()
                        txtLocation1.text = it.child("location1").value.toString()
                        txtCompanyName2.text = cn2
                        txtPosition2.text = it.child("position2").value.toString()
                        txtLocation2.text = it.child("location2").value.toString()
                        txtExp.visibility = View.VISIBLE
                        txtExpNum1.visibility = View.VISIBLE
                        llTxtExp1.visibility = View.VISIBLE
                        txtExpNum2.visibility = View.VISIBLE
                        llTxtExp2.visibility = View.VISIBLE
                    } else if (cn1 != "") {
                        txtCompanyName1.text = cn1
                        txtPosition1.text = it.child("position1").value.toString()
                        txtLocation1.text = it.child("location1").value.toString()
                        txtExp.visibility = View.VISIBLE
                        txtExpNum1.visibility = View.VISIBLE
                        llTxtExp1.visibility = View.VISIBLE
                        txtExpNum2.visibility = View.GONE
                        llTxtExp2.visibility = View.GONE
                    } else {
                        txtExp.visibility = View.GONE
                        txtExpNum1.visibility = View.GONE
                        llTxtExp1.visibility = View.GONE
                        txtExpNum2.visibility = View.GONE
                        llTxtExp2.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myReferenceDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val cnr1 = it.child("companyName1Reference").value.toString()
                    val cnr2 = it.child("companyName2Reference").value.toString()

                    if (cnr1 != "" && cnr2 != "") {
                        txtReferenceName1.text = it.child("name1Reference").value.toString()
                        txtReferenceCompanyName1.text =
                            it.child("companyName1Reference").value.toString()
                        txtReferencePosition1.text = it.child("position1Reference").value.toString()
                        txtReferencePhone1.text = it.child("phone1Reference").value.toString()
                        txtReferenceName2.text = it.child("name2Reference").value.toString()
                        txtReferenceCompanyName2.text =
                            it.child("companyName2Reference").value.toString()
                        txtReferencePosition2.text = it.child("position2Reference").value.toString()
                        txtReferencePhone2.text = it.child("phone2Reference").value.toString()
                        txtReference.visibility = View.VISIBLE
                        rlReference.visibility = View.VISIBLE
                    } else if (cnr1 != "") {
                        txtReferenceName1.text = it.child("name1Reference").value.toString()
                        txtReferenceCompanyName1.text =
                            it.child("companyName1Reference").value.toString()
                        txtReferencePosition1.text = it.child("position1Reference").value.toString()
                        txtReferencePhone1.text = it.child("phone1Reference").value.toString()
                        txtReference.visibility = View.VISIBLE
                        rlReference.visibility = View.VISIBLE
                        txtReferenceNum2.visibility = View.GONE
                        llTxtReference2.visibility = View.GONE
                    } else {
                        txtReference.visibility = View.GONE
                        txtReferenceNum1.visibility = View.GONE
                        llTxtReference1.visibility = View.GONE
                        txtReferenceNum2.visibility = View.GONE
                        llTxtReference2.visibility = View.GONE
                        rlReference.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

        } else {
            Toast(this).showCustomToast("Unsuccessful..!", this)
        }
    }

    fun selectTemplateData2() {

        val t2TxtName: TextView = findViewById(R.id.t2TxtName)
        val t2TxtDesignation: TextView = findViewById(R.id.t2TxtDesignation)
        val t2TxtAddress: TextView = findViewById(R.id.t2TxtAddress)
        val t2TxtNumber: TextView = findViewById(R.id.t2TxtNumber)
        val t2TxtEmail: TextView = findViewById(R.id.t2TxtEmail)
        val t2TxtGender: TextView = findViewById(R.id.t2TxtGender)
        val t2TxtBirthDate: TextView = findViewById(R.id.t2TxtBirthDate)
        val t2TxtBirthPlace: TextView = findViewById(R.id.t2TxtBirthPlace)
        val t2TxtCast: TextView = findViewById(R.id.t2TxtCast)
        val t2TxtMS: TextView = findViewById(R.id.t2TxtMS)
        val t2TxtLanguage: TextView = findViewById(R.id.t2TxtLanguage)
        val t2TxtVillage: TextView = findViewById(R.id.t2TxtVillage)
        val t2TxtDistrict: TextView = findViewById(R.id.t2TxtDistrict)
        val t2TxtState: TextView = findViewById(R.id.t2TxtState)
        val t2TxtNationality: TextView = findViewById(R.id.t2TxtNationality)
        val t2llExpDetail: LinearLayout = findViewById(R.id.t2llExpDetail)
        val t2LLExpFresher: LinearLayout = findViewById(R.id.t2LLExpFresher)
        val t2TxtFresher: TextView = findViewById(R.id.t2TxtFresher)
        val t2LLExp1: LinearLayout = findViewById(R.id.t2LLExp1)
        val t2TxtPosition1: TextView = findViewById(R.id.t2TxtPosition1)
        val t2TxtCompanyName1: TextView = findViewById(R.id.t2TxtCompanyName1)
        val t2TxtLocation1: TextView = findViewById(R.id.t2TxtLocation1)
        val lineViewHorizontalExp1: LinearLayout = findViewById(R.id.lineViewHorizontalExp1)
        val t2LLExp2: LinearLayout = findViewById(R.id.t2LLExp2)
        val t2TxtPosition2: TextView = findViewById(R.id.t2TxtPosition2)
        val t2TxtCompanyName2: TextView = findViewById(R.id.t2TxtCompanyName2)
        val t2TxtLocation2: TextView = findViewById(R.id.t2TxtLocation2)
        val lineViewHorizontalExp2: LinearLayout = findViewById(R.id.lineViewHorizontalExp2)
        val t2LLExp3: LinearLayout = findViewById(R.id.t2LLExp3)
        val t2TxtPosition3: TextView = findViewById(R.id.t2TxtPosition3)
        val t2TxtCompanyName3: TextView = findViewById(R.id.t2TxtCompanyName3)
        val t2TxtLocation3: TextView = findViewById(R.id.t2TxtLocation3)
        val lineViewHorizontalExp3: LinearLayout = findViewById(R.id.lineViewHorizontalExp3)
        val t2llEducationDetail: LinearLayout = findViewById(R.id.t2llEducationDetail)
        val row1: TableRow = findViewById(R.id.row1)
        val row2: TableRow = findViewById(R.id.row2)
        val t2TxtYear10: TextView = findViewById(R.id.t2TxtYear10)
        val t2TxtSchoolUniversity10: TextView = findViewById(R.id.t2TxtSchoolUniversity10)
        val t2TxtDegree10: TextView = findViewById(R.id.t2TxtDegree10)
        val t2TxtPercentage10: TextView = findViewById(R.id.t2TxtPercentage10)
        val row3: TableRow = findViewById(R.id.row3)
        val t2TxtYear12: TextView = findViewById(R.id.t2TxtYear12)
        val t2TxtSchoolUniversity12: TextView = findViewById(R.id.t2TxtSchoolUniversity12)
        val t2TxtDegree12: TextView = findViewById(R.id.t2TxtDegree12)
        val t2TxtPercentage12: TextView = findViewById(R.id.t2TxtPercentage12)
        val row4: TableRow = findViewById(R.id.row4)
        val t2TxtYearUG: TextView = findViewById(R.id.t2TxtYearUG)
        val t2TxtSchoolUniversityUG: TextView = findViewById(R.id.t2TxtSchoolUniversityUG)
        val t2TxtDegreeUG: TextView = findViewById(R.id.t2TxtDegreeUG)
        val t2TxtPercentageUG: TextView = findViewById(R.id.t2TxtPercentageUG)
        val row5: TableRow = findViewById(R.id.row5)
        val t2TxtYearPG: TextView = findViewById(R.id.t2TxtYearPG)
        val t2TxtSchoolUniversityPG: TextView = findViewById(R.id.t2TxtSchoolUniversityPG)
        val t2TxtDegreePG: TextView = findViewById(R.id.t2TxtDegreePG)
        val t2TxtPercentagePG: TextView = findViewById(R.id.t2TxtPercentagePG)
        val t2llSkillDetail: LinearLayout = findViewById(R.id.t2llSkillDetail)
        val lineVerticalSkill1: LinearLayout = findViewById(R.id.lineVerticalSkill1)
        val t2TxtSkill1: TextView = findViewById(R.id.t2TxtSkill1)
        val lineVerticalSkill2: LinearLayout = findViewById(R.id.lineVerticalSkill2)
        val t2TxtSkill2: TextView = findViewById(R.id.t2TxtSkill2)
        val lineVerticalSkill3: LinearLayout = findViewById(R.id.lineVerticalSkill3)
        val t2TxtSkill3: TextView = findViewById(R.id.t2TxtSkill3)
        val lineVerticalSkill4: LinearLayout = findViewById(R.id.lineVerticalSkill4)

        if (number.isNotEmpty()) {
            myPersonalDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    iamgeFatch()
                    t2TxtName.text = it.child("name").value.toString()
                    t2TxtAddress.text = it.child("address").value.toString()
                    t2TxtNumber.text = it.child("number").value.toString()
                    t2TxtEmail.text = it.child("email").value.toString()
                    t2TxtGender.text = it.child("gender").value.toString()
                    t2TxtBirthDate.text = it.child("birthDate").value.toString()
                    t2TxtBirthPlace.text = it.child("birthPlace").value.toString()
                    t2TxtCast.text = it.child("cast").value.toString()
                    t2TxtMS.text = it.child("maritialStatus").value.toString()
                    t2TxtLanguage.text = it.child("languageKnown").value.toString()
                    t2TxtVillage.text = it.child("vilage").value.toString()
                    t2TxtDistrict.text = it.child("district").value.toString()
                    t2TxtState.text = it.child("state").value.toString()
                    t2TxtNationality.text = it.child("nationality").value.toString()
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myExperienceDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val f = it.child("fresher").value.toString()
                    val cn1 = it.child("companyName1").value.toString()
                    val cn2 = it.child("companyName2").value.toString()
                    val cn3 = it.child("companyName3").value.toString()
                    if (f == "Yes") {
                        t2llExpDetail.visibility = View.VISIBLE
                        t2LLExpFresher.visibility = View.VISIBLE
                        t2TxtFresher.text = "Yes, I'm Fresher."
                        t2TxtDesignation.text = "Fresher"
                        t2LLExp1.visibility = View.GONE
                        t2LLExp2.visibility = View.GONE
                        t2LLExp3.visibility = View.GONE
                    } else if (cn1 != "" && cn2 != "" && cn3 != "") {
                        t2TxtCompanyName1.text = "[$cn1]"
                        t2TxtPosition1.text = it.child("position1").value.toString()
                        t2TxtLocation1.text = it.child("location1").value.toString()
                        t2TxtCompanyName2.text = "[$cn2]"
                        t2TxtPosition2.text = it.child("position2").value.toString()
                        t2TxtLocation2.text = it.child("location2").value.toString()
                        t2TxtCompanyName3.text = "[$cn3]"
                        t2TxtPosition3.text = it.child("position3").value.toString()
                        t2TxtDesignation.text = "[" + t2TxtPosition3.text + "]"
                        t2TxtLocation3.text = it.child("location3").value.toString()
                        t2llExpDetail.visibility = View.VISIBLE
                        t2LLExp1.visibility = View.VISIBLE
                        lineViewHorizontalExp1.visibility = View.VISIBLE
                        t2LLExp2.visibility = View.VISIBLE
                        lineViewHorizontalExp2.visibility = View.VISIBLE
                        t2LLExp3.visibility = View.VISIBLE
                        lineViewHorizontalExp3.visibility = View.VISIBLE
                    } else if (cn1 != "" && cn2 != "") {
                        t2TxtCompanyName1.text = "[$cn1]"
                        t2TxtPosition1.text = it.child("position1").value.toString()
                        t2TxtLocation1.text = it.child("location1").value.toString()
                        t2TxtCompanyName2.text = "[$cn2]"
                        t2TxtPosition2.text = it.child("position2").value.toString()
                        t2TxtDesignation.text = "[" + t2TxtPosition2.text + "]"
                        t2TxtLocation2.text = it.child("location2").value.toString()
                        t2llExpDetail.visibility = View.VISIBLE
                        t2LLExp1.visibility = View.VISIBLE
                        lineViewHorizontalExp1.visibility = View.VISIBLE
                        t2LLExp2.visibility = View.VISIBLE
                        lineViewHorizontalExp2.visibility = View.VISIBLE
                        t2LLExp3.visibility = View.GONE
                        lineViewHorizontalExp3.visibility = View.GONE
                    } else if (cn1 != "") {
                        t2TxtCompanyName1.text = "[$cn1]"
                        t2TxtPosition1.text = it.child("position1").value.toString()
                        t2TxtDesignation.text = "[" + t2TxtPosition1.text + "]"
                        t2TxtLocation1.text = it.child("location1").value.toString()
                        t2llExpDetail.visibility = View.VISIBLE
                        t2LLExp1.visibility = View.VISIBLE
                        lineViewHorizontalExp1.visibility = View.VISIBLE
                        t2LLExp2.visibility = View.GONE
                        lineViewHorizontalExp2.visibility = View.GONE
                        t2LLExp3.visibility = View.GONE
                        lineViewHorizontalExp3.visibility = View.GONE
                    } else {
                        t2llExpDetail.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myEducationDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val passY10 = it.child("passingYear10").value.toString()
                    val passY12 = it.child("passingYear12").value.toString()
                    val passUG = it.child("passingYearGraduation").value.toString()
                    val passPG = it.child("passingYearPostGraduation").value.toString()
                    if (passY10 != "" && passY12 != "" && passUG != "" && passPG != "") {
                        t2TxtYear10.text = passY10
                        t2TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t2TxtDegree10.text = it.child("degreeName10").value.toString()
                        t2TxtPercentage10.text = it.child("percentage10").value.toString()
                        t2TxtYear12.text = passY12
                        t2TxtSchoolUniversity12.text = it.child("schoolName12").value.toString()
                        t2TxtDegree12.text = it.child("degreeName12").value.toString()
                        t2TxtPercentage12.text = it.child("percentage12").value.toString()
                        t2TxtYearUG.text = passUG
                        t2TxtSchoolUniversityUG.text =
                            it.child("universityNameGraduation").value.toString()
                        t2TxtDegreeUG.text = it.child("degreeNameGraduation").value.toString()
                        t2TxtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        t2TxtYearPG.text = passPG
                        t2TxtSchoolUniversityPG.text =
                            it.child("universityNamePostGraduation").value.toString()
                        t2TxtDegreePG.text = it.child("degreeNamePostGraduation").value.toString()
                        t2TxtPercentagePG.text =
                            it.child("percentagePostGraduation").value.toString()
                        t2llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE
                        row4.visibility = View.VISIBLE
                        row5.visibility = View.VISIBLE
                    } else if (passY10 != "" && passY12 != "" && passUG != "") {
                        t2TxtYear10.text = passY10
                        t2TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t2TxtDegree10.text = it.child("degreeName10").value.toString()
                        t2TxtPercentage10.text = it.child("percentage10").value.toString()
                        t2TxtYear12.text = passY12
                        t2TxtSchoolUniversity12.text = it.child("schoolName12").value.toString()
                        t2TxtDegree12.text = it.child("degreeName12").value.toString()
                        t2TxtPercentage12.text = it.child("percentage12").value.toString()
                        t2TxtYearUG.text = passUG
                        t2TxtSchoolUniversityUG.text =
                            it.child("universityNameGraduation").value.toString()
                        t2TxtDegreeUG.text = it.child("degreeNameGraduation").value.toString()
                        t2TxtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        t2llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE
                        row4.visibility = View.VISIBLE
                        row5.visibility = View.GONE
                    } else if (passY10 != "" && passY12 != "") {
                        t2TxtYear10.text = passY10
                        t2TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t2TxtDegree10.text = it.child("degreeName10").value.toString()
                        t2TxtPercentage10.text = it.child("percentage10").value.toString()
                        t2TxtYear12.text = passY12
                        t2TxtSchoolUniversity12.text = it.child("schoolName12").value.toString()
                        t2TxtDegree12.text = it.child("degreeName12").value.toString()
                        t2TxtPercentage12.text = it.child("percentage12").value.toString()
                        t2llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE
                        row4.visibility = View.GONE
                        row5.visibility = View.GONE
                    } else if (passY10 != "") {
                        t2TxtYear10.text = passY10
                        t2TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t2TxtDegree10.text = it.child("degreeName10").value.toString()
                        t2TxtPercentage10.text = it.child("percentage10").value.toString()
                        t2llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.GONE
                        row4.visibility = View.GONE
                        row5.visibility = View.GONE
                    } else {
                        t2llEducationDetail.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            mySkillDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val s1 = it.child("skillName1").value.toString()
                    val s2 = it.child("skillName2").value.toString()
                    val s3 = it.child("skillName3").value.toString()
                    if (s1 != "" && s2 != "" && s3 != "") {
                        t2TxtSkill1.text = s1
                        t2TxtSkill2.text = s2
                        t2TxtSkill3.text = s3
                        t2llSkillDetail.visibility = View.VISIBLE
                        lineVerticalSkill1.visibility = View.VISIBLE
                        lineVerticalSkill2.visibility = View.VISIBLE
                        lineVerticalSkill3.visibility = View.VISIBLE
                        lineVerticalSkill4.visibility = View.VISIBLE
                    } else if (s1 != "" && s2 != "") {
                        t2TxtSkill1.text = s1
                        t2TxtSkill2.text = s2
                        t2llSkillDetail.visibility = View.VISIBLE
                        lineVerticalSkill1.visibility = View.VISIBLE
                        lineVerticalSkill2.visibility = View.VISIBLE
                    } else if (s1 != "") {
                        t2TxtSkill1.text = s1
                        t2llSkillDetail.visibility = View.VISIBLE
                        lineVerticalSkill1.visibility = View.VISIBLE
                    } else {
                        t2llSkillDetail.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

        } else {
            Toast(this).showCustomToast("Unsuccessful..!", this)
        }
    }

    fun selectTemplateData3() {
        val t3TxtName: TextView = findViewById(R.id.t3TxtName)
        val t3TxtDesignation: TextView = findViewById(R.id.t3TxtDesignation)
        val t3TxtAddress: TextView = findViewById(R.id.t3TxtAddress)
        val t3TxtNumber: TextView = findViewById(R.id.t3TxtNumber)
        val t3TxtEmail: TextView = findViewById(R.id.t3TxtEmail)
        val t3TxtGender: TextView = findViewById(R.id.t3TxtGender)
        val t3TxtBirthDate: TextView = findViewById(R.id.t3TxtBirthDate)
        val t3TxtBirthPlace: TextView = findViewById(R.id.t3TxtBirthPlace)
        val t3TxtCast: TextView = findViewById(R.id.t3TxtCast)
        val t3TxtMS: TextView = findViewById(R.id.t3TxtMS)
        val t3TxtLanguage: TextView = findViewById(R.id.t3TxtLanguage)
        val t3TxtNationality: TextView = findViewById(R.id.t3TxtNationality)
        val t3llEducationDetail: LinearLayout = findViewById(R.id.t3llEducationDetail)
        val row1: TableRow = findViewById(R.id.row1)
        val row2: TableRow = findViewById(R.id.row2)
        val row3: TableRow = findViewById(R.id.row3)
        val row4: TableRow = findViewById(R.id.row4)
        val row5: TableRow = findViewById(R.id.row5)
        val t3TxtYear10: TextView = findViewById(R.id.t3TxtYear10)
        val t3TxtSchoolUniversity10: TextView = findViewById(R.id.t3TxtSchoolUniversity10)
        val t3TxtDegree10: TextView = findViewById(R.id.t3TxtDegree10)
        val t3TxtPercentage10: TextView = findViewById(R.id.t3TxtPercentage10)
        val t3TxtYear12: TextView = findViewById(R.id.t3TxtYear12)
        val t3TxtSchoolUniversity12: TextView = findViewById(R.id.t3TxtSchoolUniversity12)
        val t3TxtDegree12: TextView = findViewById(R.id.t3TxtDegree12)
        val t3TxtPercentage12: TextView = findViewById(R.id.t3TxtPercentage12)
        val t3TxtYearUG: TextView = findViewById(R.id.t3TxtYearUG)
        val t3TxtSchoolUniversityUG: TextView = findViewById(R.id.t3TxtSchoolUniversityUG)
        val t3TxtDegreeUG: TextView = findViewById(R.id.t3TxtDegreeUG)
        val t3TxtPercentageUG: TextView = findViewById(R.id.t3TxtPercentageUG)
        val t3TxtYearPG: TextView = findViewById(R.id.t3TxtYearPG)
        val t3TxtSchoolUniversityPG: TextView = findViewById(R.id.t3TxtSchoolUniversityPG)
        val t3TxtDegreePG: TextView = findViewById(R.id.t3TxtDegreePG)
        val t3TxtPercentagePG: TextView = findViewById(R.id.t3TxtPercentagePG)
        val t3llSkillDetail: LinearLayout = findViewById(R.id.t3llSkillDetail)
        val t3TxtSkill1: TextView = findViewById(R.id.t3TxtSkill1)
        val t3TxtSkill2: TextView = findViewById(R.id.t3TxtSkill2)
        val t3TxtSkill3: TextView = findViewById(R.id.t3TxtSkill3)
        val t3TxtSkill4: TextView = findViewById(R.id.t3TxtSkill4)
        val t3llExpDetail: LinearLayout = findViewById(R.id.t3llExpDetail)
        val t3LLExpFresher: LinearLayout = findViewById(R.id.t3LLExpFresher)
        val t3TxtFresher: TextView = findViewById(R.id.t3TxtFresher)
        val t3LLExp1: LinearLayout = findViewById(R.id.t3LLExp1)
        val t3TxtPosition1: TextView = findViewById(R.id.t3TxtPosition1)
        val t3TxtCompanyName1: TextView = findViewById(R.id.t3TxtCompanyName1)
        val t3TxtLocation1: TextView = findViewById(R.id.t3TxtLocation1)
        val t3LLExp2: LinearLayout = findViewById(R.id.t3LLExp2)
        val t3TxtPosition2: TextView = findViewById(R.id.t3TxtPosition2)
        val t3TxtCompanyName2: TextView = findViewById(R.id.t3TxtCompanyName2)
        val t3TxtLocation2: TextView = findViewById(R.id.t3TxtLocation2)
        val t3llRefDetail: LinearLayout = findViewById(R.id.t3llRefDetail)
        val t3LLRef1: LinearLayout = findViewById(R.id.t3LLRef1)
        val t3TxtRefName1: TextView = findViewById(R.id.t3TxtRefName1)
        val t3TxtRefCompanyName1: TextView = findViewById(R.id.t3TxtRefCompanyName1)
        val t3TxtRefPosition1: TextView = findViewById(R.id.t3TxtRefPosition1)
        val t3TxtRefPhone1: TextView = findViewById(R.id.t3TxtRefPhone1)
        val t3LLRef2: LinearLayout = findViewById(R.id.t3LLRef2)
        val t3TxtRefName2: TextView = findViewById(R.id.t3TxtRefName2)
        val t3TxtRefCompanyName2: TextView = findViewById(R.id.t3TxtRefCompanyName2)
        val t3TxtRefPosition2: TextView = findViewById(R.id.t3TxtRefPosition2)
        val t3TxtRefPhone2: TextView = findViewById(R.id.t3TxtRefPhone2)

        if (number.isNotEmpty()) {

            myPersonalDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    iamgeFatch()
                    t3TxtName.text = it.child("name").value.toString()
                    t3TxtAddress.text = it.child("address").value.toString()
                    t3TxtNumber.text = it.child("number").value.toString()
                    t3TxtEmail.text = it.child("email").value.toString()
                    t3TxtGender.text = it.child("gender").value.toString()
                    t3TxtBirthDate.text = it.child("birthDate").value.toString()
                    t3TxtBirthPlace.text = it.child("birthPlace").value.toString()
                    t3TxtCast.text = it.child("cast").value.toString()
                    t3TxtMS.text = it.child("maritialStatus").value.toString()
                    t3TxtLanguage.text = it.child("languageKnown").value.toString()
                    t3TxtNationality.text = it.child("nationality").value.toString()
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myEducationDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val passY10 = it.child("passingYear10").value.toString()
                    val passY12 = it.child("passingYear12").value.toString()
                    val passUG = it.child("passingYearGraduation").value.toString()
                    val passPG = it.child("passingYearPostGraduation").value.toString()
                    if (passY10 != "" && passY12 != "" && passUG != "" && passPG != "") {
                        t3TxtYear10.text = passY10
                        t3TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t3TxtDegree10.text = it.child("degreeName10").value.toString()
                        t3TxtPercentage10.text = it.child("percentage10").value.toString()
                        t3TxtYear12.text = passY12
                        t3TxtSchoolUniversity12.text = it.child("schoolName12").value.toString()
                        t3TxtDegree12.text = it.child("degreeName12").value.toString()
                        t3TxtPercentage12.text = it.child("percentage12").value.toString()
                        t3TxtYearUG.text = passUG
                        t3TxtSchoolUniversityUG.text =
                            it.child("universityNameGraduation").value.toString()
                        t3TxtDegreeUG.text = it.child("degreeNameGraduation").value.toString()
                        t3TxtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        t3TxtYearPG.text = passPG
                        t3TxtSchoolUniversityPG.text =
                            it.child("universityNamePostGraduation").value.toString()
                        t3TxtDegreePG.text = it.child("degreeNamePostGraduation").value.toString()
                        t3TxtPercentagePG.text =
                            it.child("percentagePostGraduation").value.toString()
                        t3llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE
                        row4.visibility = View.VISIBLE
                        row5.visibility = View.VISIBLE
                    } else if (passY10 != "" && passY12 != "" && passUG != "") {
                        t3TxtYear10.text = passY10
                        t3TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t3TxtDegree10.text = it.child("degreeName10").value.toString()
                        t3TxtPercentage10.text = it.child("percentage10").value.toString()
                        t3TxtYear12.text = passY12
                        t3TxtSchoolUniversity12.text = it.child("schoolName12").value.toString()
                        t3TxtDegree12.text = it.child("degreeName12").value.toString()
                        t3TxtPercentage12.text = it.child("percentage12").value.toString()
                        t3TxtYearUG.text = passUG
                        t3TxtSchoolUniversityUG.text =
                            it.child("universityNameGraduation").value.toString()
                        t3TxtDegreeUG.text = it.child("degreeNameGraduation").value.toString()
                        t3TxtPercentageUG.text = it.child("percentageGraduation").value.toString()
                        t3llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE
                        row4.visibility = View.VISIBLE
                        row5.visibility = View.GONE
                    } else if (passY10 != "" && passY12 != "") {
                        t3TxtYear10.text = passY10
                        t3TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t3TxtDegree10.text = it.child("degreeName10").value.toString()
                        t3TxtPercentage10.text = it.child("percentage10").value.toString()
                        t3TxtYear12.text = passY12
                        t3TxtSchoolUniversity12.text = it.child("schoolName12").value.toString()
                        t3TxtDegree12.text = it.child("degreeName12").value.toString()
                        t3TxtPercentage12.text = it.child("percentage12").value.toString()
                        t3llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE
                        row4.visibility = View.GONE
                        row5.visibility = View.GONE
                    } else if (passY10 != "") {
                        t3TxtYear10.text = passY10
                        t3TxtSchoolUniversity10.text = it.child("schoolName10").value.toString()
                        t3TxtDegree10.text = it.child("degreeName10").value.toString()
                        t3TxtPercentage10.text = it.child("percentage10").value.toString()
                        t3llEducationDetail.visibility = View.VISIBLE
                        row1.visibility = View.VISIBLE
                        row2.visibility = View.VISIBLE
                        row3.visibility = View.GONE
                        row4.visibility = View.GONE
                        row5.visibility = View.GONE
                    } else {
                        t3llEducationDetail.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            mySkillDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val s1 = it.child("skillName1").value.toString()
                    val s2 = it.child("skillName2").value.toString()
                    val s3 = it.child("skillName3").value.toString()
                    val s4 = it.child("skillName4").value.toString()
                    if (s1 != "" && s2 != "" && s3 != "" && s4 != "") {
                        t3TxtSkill1.text = "\u25CB $s1"
                        t3TxtSkill2.text = "\u25CB $s2"
                        t3TxtSkill3.text = "\u25CB $s3"
                        t3TxtSkill4.text = "\u25CB $s4"
                        t3llSkillDetail.visibility = View.VISIBLE
                    } else if (s1 != "" && s2 != "" && s3 != "") {
                        t3TxtSkill1.text = "\u25CB $s1"
                        t3TxtSkill2.text = "\u25CB $s2"
                        t3TxtSkill3.text = "\u25CB $s3"
                        t3llSkillDetail.visibility = View.VISIBLE
                    } else if (s1 != "" && s2 != "") {
                        t3TxtSkill1.text = "\u25CB $s1"
                        t3TxtSkill2.text = "\u25CB $s2"
                        t3llSkillDetail.visibility = View.VISIBLE
                    } else if (s1 != "") {
                        t3TxtSkill1.text = "\u25CB $s1"
                        t3llSkillDetail.visibility = View.VISIBLE
                    } else {
                        t3llSkillDetail.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myExperienceDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val f = it.child("fresher").value.toString()
                    val cn1 = it.child("companyName1").value.toString()
                    val cn2 = it.child("companyName2").value.toString()
                    if (f == "Yes") {
                        t3TxtDesignation.text = "Fresher"
                        t3llExpDetail.visibility = View.VISIBLE
                        t3LLExpFresher.visibility = View.VISIBLE
                        t3TxtFresher.text = "Yes, I'm a Fresher."
                        t3LLExp1.visibility = View.GONE
                        t3LLExp2.visibility = View.GONE
                    } else if (cn1 != "" && cn2 != "") {
                        t3TxtCompanyName1.text = "    " + cn1
                        t3TxtPosition1.text = "\u25CB " + it.child("position1").value.toString()
                        t3TxtLocation1.text = "    " + it.child("location1").value.toString()
                        t3TxtCompanyName2.text = "    " + cn2
                        t3TxtPosition2.text = "\u25CB " + it.child("position2").value.toString()
                        t3TxtLocation2.text = "    " + it.child("location2").value.toString()
                        t3TxtDesignation.text = "[" + it.child("position2").value.toString() + "]"
                        t3llExpDetail.visibility = View.VISIBLE
                        t3LLExp1.visibility = View.VISIBLE
                        t3LLExp2.visibility = View.VISIBLE
                    } else if (cn1 != "") {
                        t3TxtCompanyName1.text = "    " + cn1
                        t3TxtPosition1.text = "\u25CB " + it.child("position1").value.toString()
                        t3TxtLocation1.text = "    " + it.child("location1").value.toString()
                        t3TxtDesignation.text = "[" + it.child("position3").value.toString() + "]"
                        t3llExpDetail.visibility = View.VISIBLE
                        t3LLExp1.visibility = View.VISIBLE
                        t3LLExp2.visibility = View.GONE
                    } else {
                        t3llExpDetail.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }

            myReferenceDR.child(number).get().addOnSuccessListener {
                if (it.exists()) {
                    val cnr1 = it.child("companyName1Reference").value.toString()
                    val cnr2 = it.child("companyName2Reference").value.toString()

                    if (cnr1 != "" && cnr2 != "") {
                        t3TxtRefName1.text = "\u25CB " + it.child("name1Reference").value.toString()
                        t3TxtRefCompanyName1.text =
                            "    " + it.child("companyName1Reference").value.toString()
                        t3TxtRefPosition1.text =
                            "    " + it.child("position1Reference").value.toString()
                        t3TxtRefPhone1.text = "    " + it.child("phone1Reference").value.toString()
                        t3TxtRefName2.text = "\u25CB " + it.child("name2Reference").value.toString()
                        t3TxtRefCompanyName2.text =
                            "    " + it.child("companyName2Reference").value.toString()
                        t3TxtRefPosition2.text =
                            "    " + it.child("position2Reference").value.toString()
                        t3TxtRefPhone2.text = "    " + it.child("phone2Reference").value.toString()
                        t3llRefDetail.visibility = View.VISIBLE
                        t3LLRef1.visibility = View.VISIBLE
                        t3LLRef2.visibility = View.VISIBLE
                    } else if (cnr1 != "") {
                        t3TxtRefName1.text = "\u25CB " + it.child("name1Reference").value.toString()
                        t3TxtRefCompanyName1.text =
                            "    " + it.child("companyName1Reference").value.toString()
                        t3TxtRefPosition1.text =
                            "    " + it.child("position1Reference").value.toString()
                        t3TxtRefPhone1.text = "    " + it.child("phone1Reference").value.toString()
                        t3llRefDetail.visibility = View.VISIBLE
                        t3LLRef1.visibility = View.VISIBLE
                        t3LLRef2.visibility = View.GONE
                    } else {
                        t3llRefDetail.visibility = View.GONE
                        t3LLRef1.visibility = View.GONE
                        t3LLRef2.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                Toast(this).showCustomToast("Failed", this)
            }
        }
    }

    fun iamgeFatch() {
        val showImageView: ImageView = findViewById(R.id.showImageView)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching image....!")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val imageName = number
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName")
        val localfile = File.createTempFile("tempImage", "JPG")

        storageRef.getFile(localfile).addOnSuccessListener {

            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            showImageView.setImageBitmap(bitmap)
        }.addOnFailureListener {
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
            Toast.makeText(this, "Please, Upload The Image", Toast.LENGTH_SHORT).show()
        }
    }


}
