package com.pmgohil.resumecenter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var getMoreRL: RelativeLayout
    lateinit var createResumeRL: RelativeLayout
    lateinit var rateRL: RelativeLayout
    lateinit var settingRL: RelativeLayout
    lateinit var shareOnSocialMediaRL: RelativeLayout
    lateinit var logoutRL: RelativeLayout
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var MyFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMoreRL = findViewById(R.id.getMoreRL)
        createResumeRL = findViewById(R.id.createResumeRL)
        rateRL = findViewById(R.id.rateRL)
        settingRL = findViewById(R.id.settingRL)
        shareOnSocialMediaRL = findViewById(R.id.shareRL)
        logoutRL = findViewById(R.id.logoutRL)

        rateRL.setOnClickListener() {
            ratingInPlayStore()
        }
        settingRL.setOnClickListener() {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        shareOnSocialMediaRL.setOnClickListener() {
            socialMediaSharing()
        }

        createResumeRL.setOnClickListener() {
            startActivity(Intent(this, GetPersonalInfoActivity::class.java))
        }
        getMoreRL.setOnClickListener() {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Pm+Gohil")
                )
            )
        }

        MyFirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        logoutRL.setOnClickListener() {
            /*val mBuilder = AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to exit?")

                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show()

            // Function for the positive button
            // is programmed to exit the application
            val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
            mPositiveButton.setOnClickListener {
                exitProcess(0)
            }*/
            googleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    // gives the rate in playstore resume center app
    @SuppressLint("InlinedApi")
    fun ratingInPlayStore() {
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        startActivity(goToMarket)
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.basiccalculator.calculator")//$packageName <- set after id=

            )
        )
    }

    // share app on social media
    fun socialMediaSharing() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://pmgohil45.blogspot.com/2023/02/resume-center.html/")
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "Resume Center")
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
