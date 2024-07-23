package com.example.collegeclub

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    private var backPressedTime = 0L
    /*   private lateinit var networkConnectionClass: networkConnection
       private lateinit var networkImg: ImageView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

   /*     MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)*/
        //check net connection
        /*checkNetworkConnection()

        networkImg = findViewById(R.id.networkImg)
        */
        //hide action bar
        supportActionBar?.hide()


        //let's create out connection manager
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        //now let's check the connection and display the information in our layout
        if (isConnected) {

            var syllabus = findViewById<Button>(R.id.syllabusBtn)
            syllabus.setOnClickListener {
                syllabus()
            }

            var sad = findViewById<Button>(R.id.subBtn1)
            sad.setOnClickListener {
                sad()
            }

            var cpp = findViewById<Button>(R.id.subBtn2)
            cpp.setOnClickListener {
                cpp()
            }

            var oracle = findViewById<Button>(R.id.subBtn3)
            oracle.setOnClickListener {
                oracle()
            }

            var wordpress = findViewById<Button>(R.id.subBtn4)
            wordpress.setOnClickListener {
                wordpress()
            }

            var privacy_policy = findViewById<Button>(R.id.privacy_policyBtn)
            privacy_policy.setOnClickListener {
                privacy_policy()
            }

            var footerManage = findViewById<TextView>(R.id.footerTxt)
            footerManage.setOnClickListener {
                footerManage()
            }
        } else {
            network()
        }
    }


    fun syllabus() {
        val s = Intent(this@MainActivity, syllabus::class.java)
        startActivity(s)
    }

    fun sad() {
        val s = Intent(this@MainActivity, sub1::class.java)
        startActivity(s)
    }

    fun cpp() {
        val c = Intent(this@MainActivity, sub2::class.java)
        startActivity(c)
    }

    fun oracle() {
        val o = Intent(this@MainActivity, sub3::class.java)
        startActivity(o)
    }

    fun wordpress() {
        val w = Intent(this@MainActivity, sub4::class.java)
        startActivity(w)
    }

    fun privacy_policy() {
        val p_p = Intent(this@MainActivity, privacy_policy::class.java)
        startActivity(p_p)
    }

    fun network() {
        val network = Intent(this@MainActivity, network::class.java)
        startActivity(network)
        finish()
    }

    fun footerManage() {
        val footer = Intent(this@MainActivity, footerManage::class.java)
        startActivity(footer)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(applicationContext, "Double tap to back...!", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()

/*      val builder = AlertDialog.Builder(this, R.style.alertDialagTheme)
        builder.setTitle("Exit App")
        builder.setIcon(R.drawable.icon_warning)
        builder.setMessage("Are you sure, you want to exit..?")
         builder.setPositiveButton("Yes") { dialog, which ->
             setResult(2, intent)
             finish()
         }
         builder.setNegativeButton("No") { dialog, which ->
             Toast.makeText(this, "Clicked on no button...!", Toast.LENGTH_LONG).show()
         }
         builder.setNeutralButton("Cancel") { dialog, which ->
             Toast.makeText(this, "Clicked on cancel button...!", Toast.LENGTH_LONG).show()
         }
        val dialog: AlertDialog = builder.create()
        dialog.show()
*/
    }
/*
    private fun checkNetworkConnection() {
        networkConnectionClass = networkConnection(application)

        networkConnectionClass.observe(this, { isConnected ->
            if (isConnected) {
                networkImg.visibility = view.GONE
            } else {
                networkImg.visibility = View.VISIBLE
            }
        }
        )
    }*/

}