package com.example.pmkisan

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class guidelines : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guidelines)
        //which u went to see ads in activity
        MobileAds.initialize(
            this
        ) { }

        // Load an ad
        var adRequest = AdRequest.Builder().build()

/*        InterstitialAd.load(
            this,
            getString(R.string.interstitial_ads),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(ContentValues.TAG, adError?.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(ContentValues.TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })

        // Set the FullScreenContentCallback
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(ContentValues.TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(ContentValues.TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }*/
        // Banner ad
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)

        title = "Guidelines"

        val cardResurved = findViewById<CardView>(R.id.cardResurved)
        cardResurved.setOnClickListener() {
            openCardResurved()
        }
        val cardFinanc = findViewById<CardView>(R.id.cardFinanc)
        cardFinanc.setOnClickListener() {
            openCardFinanc()
        }
    }

    fun openCardResurved() {
        val cardResurved = Intent(this@guidelines, guidlinesMenu1::class.java)
        startActivity(cardResurved)

        // Show the ad
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    fun openCardFinanc() {
        val cardFinanc = Intent(this@guidelines, guidlinesMenu2::class.java)
        startActivity(cardFinanc)

        // Show the ad
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}