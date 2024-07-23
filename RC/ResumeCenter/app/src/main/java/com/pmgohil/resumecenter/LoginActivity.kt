package com.pmgohil.resumecenter

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    lateinit var number: EditText
    lateinit var mobileOtp: EditText
    lateinit var loginWithOtp: RelativeLayout
    lateinit var otpCheck: TextView
    lateinit var login: CardView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val req_Code: Int = 123
    lateinit var otpId: String
    lateinit var phoneNumber: String
    lateinit var countryCodePicker: CountryCodePicker
    lateinit var ccp: String
    lateinit var loginRL: RelativeLayout

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        otpCheck = findViewById(R.id.otpCheck)
        countryCodePicker = findViewById(R.id.countryCodePicker)
        number = findViewById(R.id.etNumber)
        mobileOtp = findViewById(R.id.etMobileOtp)
        loginWithOtp = findViewById(R.id.loginOtpRL)
        loginRL = findViewById(R.id.loginRL)
        //  countryCodePicker.setBackgroundColor()
        firebaseAuth = FirebaseAuth.getInstance()

        loginWithOtp.setOnClickListener() {
            otpSend()
            if (number != null && number.length() == 10) {
                mobileOtp.visibility = View.VISIBLE
                mobileOtp.focusable
            }
        }
        mobileOtp.addTextChangedListener((object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                otpCheck.setText(s)
                if (otpCheck.length() == 6) {
                    otpVerify()
                }
            }
        }))

        loginRL.setOnClickListener() { login() }

    }

    fun login() {

        FirebaseApp.initializeApp(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInGoogle()

    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun otpSend() {
        ccp = countryCodePicker.selectedCountryCode
        phoneNumber = "+" + ccp + number.text.toString()
        if (phoneNumber.isEmpty()) {
            Toast(this).showCustomToast("Invalid Phone Number!", this)
        } else if (number.text.length != 10) {
            Toast(this).showCustomToast("Enter a Valid Phone Number!", this)
        } else {
            initiatOtp()
        }
    }

    fun initiatOtp() {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber.toString())       // Phone number to verify
            .setTimeout(2L, TimeUnit.MINUTES) // Timeout and unit -
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        //when sim is not in your divice
        override fun onCodeSent(
            s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            otpId = s
            super.onCodeSent(s, forceResendingToken)
        }

        //when sim is in your divice
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            failOtp()
            Log.e("prakash", "Error ---> $e");
        }

    }

    fun failOtp() {
        Toast(this).showCustomToast("Failed...", this)
    }

    fun signInWithPhoneAuthCredential(phoneAuthCredential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

            } else {
                Log.e("prakash", "onComplete:" + (task.exception?.localizedMessage));
                //Toast(this).showCustomToast("Otp Expire ! ", this)
            }
        }
    }

    fun otpVerify() {
        if (mobileOtp.text.isEmpty() && mobileOtp.text.length != 6) {
            Toast(this).showCustomToast("mobile otp is blank", this)
        } else if (otpId.isEmpty()) {
            Toast(this).showCustomToast("OTP I'd is Empty", this)
        } else {
            val credential = PhoneAuthProvider.getCredential(otpId, mobileOtp.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}