package com.example.pmkisan

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import com.example.kisan.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class aboutUs : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        //which u went to see ads in activity
        MobileAds.initialize(
            this
        ) { }

        // Load an ad
        var adRequest = AdRequest.Builder().build()

        /*InterstitialAd.load(
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
            })*/

        // Set the FullScreenContentCallback
        /*mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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

        title = "About Us"
        // schemeCard
        val schemeCard = findViewById<CardView>(R.id.schemeCard)
        schemeCard.setOnClickListener() {
            openSchemeCard()
        }
        // exclusionCard
        val exclusionCard = findViewById<CardView>(R.id.exclusionCard)
        exclusionCard.setOnClickListener() {
            openExclusionCard()
        }
        // hindiCard
        val hindiCard = findViewById<CardView>(R.id.hindiCard)
        hindiCard.setOnClickListener() {
            openHindiCard()
        }
    }

    fun openSchemeCard() {
        val schemeCard = Intent(this@aboutUs, scheme::class.java)
        startActivity(schemeCard)

        // Show the ad
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    fun openExclusionCard() {
        val exclusionCard = Intent(this@aboutUs, exclusion::class.java)
        startActivity(exclusionCard)

        // Show the ad
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    fun openHindiCard() {
        val hindiCard = Intent(this@aboutUs, pm::class.java)
        startActivity(hindiCard)

        // Show the ad
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}