package com.example.kisan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.pmkisan.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.rewarded.RewardedAd

class MainActivity : AppCompatActivity() {
    private var mRewardedAd: RewardedAd? = null
    lateinit var webView: WebView
    lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Banner ad
        var adRequest = AdRequest.Builder().build()
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)
        /*  //which u went to see ads in activity
          MobileAds.initialize(
              this
          ) { }*/
        // Load an add

/*
        RewardedAd.load(
            this,
            "ca-app-pub-8269045129205865/3441647520",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                }
            })
*/


        title = "PM - KISAN योजना"

        // about cardview button
        val about = findViewById<CardView>(R.id.cardAbout)
        about.setOnClickListener() {
            openAbout()
        }

        // guidelines cardview button
        val guidelines = findViewById<CardView>(R.id.cardGuidelines)
        guidelines.setOnClickListener() {
            openGuidelines()
        }

        // circular cardview button
        val circular = findViewById<CardView>(R.id.cardCirculars)
        circular.setOnClickListener() {
            openCircular()
        }

        // Pmkmy cardview button
        val cardPmkmy = findViewById<CardView>(R.id.cardPmkmy)
        cardPmkmy.setOnClickListener() {
            openCardPmkmy()
        }
        // contact cardview button
        val cardContact = findViewById<CardView>(R.id.cardContact)
        cardContact.setOnClickListener() {
            openCardContact()
        }
        // login cardview button
        val cardLogin = findViewById<CardView>(R.id.cardLogin)
        cardLogin.setOnClickListener() {
            openCardLogin()
        }
        // csc login cardview button
        val cardCscLogin = findViewById<CardView>(R.id.cardCscLogin)
        cardCscLogin.setOnClickListener() {
            openCardCscLogin()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        // Rewarded Ad
        /*  mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
              override fun onAdShowedFullScreenContent() {
                  // Called when ad is shown.
                  Log.d(TAG, "Ad was shown.")
              }

              override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                  // Called when ad fails to show.
                  Log.d(TAG, "Ad failed to show.")
              }

              override fun onAdDismissedFullScreenContent() {
                  // Called when ad is dismissed.
                  // Set the ad reference to null so you don't show the ad a second time.
                  Log.d(TAG, "Ad was dismissed.")
                  mRewardedAd = null
              }
          }*/
/*        if (mRewardedAd != null) {
            val activityContext: Activity = this@MainActivity
            mRewardedAd!!.show(
                activityContext
            ) { rewardItem -> // Handle the reward.
                Log.d(TAG, "The user earned the reward.")
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }*/
        return true
    }

    fun openAbout() {
        val about = Intent(this@MainActivity, aboutUs::class.java)
        startActivity(about)

        // Rewarded Ad
/*
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
            }
        }
        if (mRewardedAd != null) {
            val activityContext: Activity = this@MainActivity
            mRewardedAd!!.show(
                activityContext
            ) { rewardItem -> // Handle the reward.
                Log.d(TAG, "The user earned the reward.")
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }

*/
    }

    fun openGuidelines() {
        val guidelines = Intent(this@MainActivity, guidelines::class.java)
        startActivity(guidelines)

        // Rewarded Ad
/*        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
            }
        }
        if (mRewardedAd != null) {
            val activityContext: Activity = this@MainActivity
            mRewardedAd!!.show(
                activityContext
            ) { rewardItem -> // Handle the reward.
                Log.d(TAG, "The user earned the reward.")
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }*/
    }

    fun openCircular() {
        val circular = Intent(this@MainActivity, circulars::class.java)
        startActivity(circular)
        // Rewarded Ad
        /* mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
             override fun onAdShowedFullScreenContent() {
                 // Called when ad is shown.
                 Log.d(TAG, "Ad was shown.")
             }

             override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                 // Called when ad fails to show.
                 Log.d(TAG, "Ad failed to show.")
             }

             override fun onAdDismissedFullScreenContent() {
                 // Called when ad is dismissed.
                 // Set the ad reference to null so you don't show the ad a second time.
                 Log.d(TAG, "Ad was dismissed.")
                 mRewardedAd = null
             }
         }
         if (mRewardedAd != null) {
             val activityContext: Activity = this@MainActivity
             mRewardedAd!!.show(
                 activityContext
             ) { rewardItem -> // Handle the reward.
                 Log.d(TAG, "The user earned the reward.")
                 val rewardAmount = rewardItem.amount
                 val rewardType = rewardItem.type
             }
         } else {
             Log.d(TAG, "The rewarded ad wasn't ready yet.")
         }
    */
    }

    fun openCardPmkmy() {
        val cardPmkmy = Intent(this@MainActivity, pmkmy::class.java)
        startActivity(cardPmkmy)
        // Rewarded Ad
        /*   mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
               override fun onAdShowedFullScreenContent() {
                   // Called when ad is shown.
                   Log.d(TAG, "Ad was shown.")
               }

               override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                   // Called when ad fails to show.
                   Log.d(TAG, "Ad failed to show.")
               }

               override fun onAdDismissedFullScreenContent() {
                   // Called when ad is dismissed.
                   // Set the ad reference to null so you don't show the ad a second time.
                   Log.d(TAG, "Ad was dismissed.")
                   mRewardedAd = null
               }
           }
           if (mRewardedAd != null) {
               val activityContext: Activity = this@MainActivity
               mRewardedAd!!.show(
                   activityContext
               ) { rewardItem -> // Handle the reward.
                   Log.d(TAG, "The user earned the reward.")
                   val rewardAmount = rewardItem.amount
                   val rewardType = rewardItem.type
               }
           } else {
               Log.d(TAG, "The rewarded ad wasn't ready yet.")
           }
      */
    }

    fun openCardContact() {
        val cardContact = Intent(this@MainActivity, contact::class.java)
        startActivity(cardContact)
        // Rewarded Ad
        /*   mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
               override fun onAdShowedFullScreenContent() {
                   // Called when ad is shown.
                   Log.d(TAG, "Ad was shown.")
               }

               override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                   // Called when ad fails to show.
                   Log.d(TAG, "Ad failed to show.")
               }

               override fun onAdDismissedFullScreenContent() {
                   // Called when ad is dismissed.
                   // Set the ad reference to null so you don't show the ad a second time.
                   Log.d(TAG, "Ad was dismissed.")
                   mRewardedAd = null
               }
           }
           if (mRewardedAd != null) {
               val activityContext: Activity = this@MainActivity
               mRewardedAd!!.show(
                   activityContext
               ) { rewardItem -> // Handle the reward.
                   Log.d(TAG, "The user earned the reward.")
                   val rewardAmount = rewardItem.amount
                   val rewardType = rewardItem.type
               }
           } else {
               Log.d(TAG, "The rewarded ad wasn't ready yet.")
           }
   */
    }

    fun openCardLogin() {
        val cardLogin = Intent(this@MainActivity, login::class.java)
        startActivity(cardLogin)
        // Rewarded Ad
        /*      mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                  override fun onAdShowedFullScreenContent() {
                      // Called when ad is shown.
                      Log.d(TAG, "Ad was shown.")
                  }

                  override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                      // Called when ad fails to show.
                      Log.d(TAG, "Ad failed to show.")
                  }

                  override fun onAdDismissedFullScreenContent() {
                      // Called when ad is dismissed.
                      // Set the ad reference to null so you don't show the ad a second time.
                      Log.d(TAG, "Ad was dismissed.")
                      mRewardedAd = null
                  }
              }
              if (mRewardedAd != null) {
                  val activityContext: Activity = this@MainActivity
                  mRewardedAd!!.show(
                      activityContext
                  ) { rewardItem -> // Handle the reward.
                      Log.d(TAG, "The user earned the reward.")
                      val rewardAmount = rewardItem.amount
                      val rewardType = rewardItem.type
                  }
              } else {
                  Log.d(TAG, "The rewarded ad wasn't ready yet.")
              }
          */
    }

    fun openCardCscLogin() {
        val cardCscLogin = Intent(this@MainActivity, cscLogin::class.java)
        startActivity(cardCscLogin)
        // Rewarded Ad
        /*mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
            }
        }
        if (mRewardedAd != null) {
            val activityContext: Activity = this@MainActivity
            mRewardedAd!!.show(
                activityContext
            ) { rewardItem -> // Handle the reward.
                Log.d(TAG, "The user earned the reward.")
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
  */
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.icon, menu)
        // Rewarded Ad
        /*    mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad was shown.")
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    // Called when ad fails to show.
                    Log.d(TAG, "Ad failed to show.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad was dismissed.")
                    mRewardedAd = null
                }
            }
            if (mRewardedAd != null) {
                val activityContext: Activity = this@MainActivity
                mRewardedAd!!.show(
                    activityContext
                ) { rewardItem -> // Handle the reward.
                    Log.d(TAG, "The user earned the reward.")
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                }
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
            }
       */     return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.privacy_policy -> {
                val privacy = Intent(this@MainActivity, privacy_policy::class.java)
                startActivity(privacy)
                // Rewarded Ad
                /*           mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                               override fun onAdShowedFullScreenContent() {
                                   // Called when ad is shown.
                                   Log.d(TAG, "Ad was shown.")
                               }

                               override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                   // Called when ad fails to show.
                                   Log.d(TAG, "Ad failed to show.")
                               }

                               override fun onAdDismissedFullScreenContent() {
                                   // Called when ad is dismissed.
                                   // Set the ad reference to null so you don't show the ad a second time.
                                   Log.d(TAG, "Ad was dismissed.")
                                   mRewardedAd = null
                               }
                           }
                           if (mRewardedAd != null) {
                               val activityContext: Activity = this@MainActivity
                               mRewardedAd!!.show(
                                   activityContext
                               ) { rewardItem -> // Handle the reward.
                                   Log.d(TAG, "The user earned the reward.")
                                   val rewardAmount = rewardItem.amount
                                   val rewardType = rewardItem.type
                               }
                           } else {
                               Log.d(TAG, "The rewarded ad wasn't ready yet.")
                           }
                */           return true
            }
            R.id.developer -> {
                val pm = Intent(this@MainActivity, pm::class.java)
                startActivity(pm)
                // Rewarded Ad
                /*         mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                             override fun onAdShowedFullScreenContent() {
                                 // Called when ad is shown.
                                 Log.d(TAG, "Ad was shown.")
                             }

                             override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                 // Called when ad fails to show.
                                 Log.d(TAG, "Ad failed to show.")
                             }

                             override fun onAdDismissedFullScreenContent() {
                                 // Called when ad is dismissed.
                                 // Set the ad reference to null so you don't show the ad a second time.
                                 Log.d(TAG, "Ad was dismissed.")
                                 mRewardedAd = null
                             }
                         }
                         if (mRewardedAd != null) {
                             val activityContext: Activity = this@MainActivity
                             mRewardedAd!!.show(
                                 activityContext
                             ) { rewardItem -> // Handle the reward.
                                 Log.d(TAG, "The user earned the reward.")
                                 val rewardAmount = rewardItem.amount
                                 val rewardType = rewardItem.type
                             }
                         } else {
                             Log.d(TAG, "The rewarded ad wasn't ready yet.")
                         }
               */          return true
            }
            R.id.icon -> {
                //Toast.makeText(applicationContext, "click on pm", Toast.LENGTH_LONG).show()
                val insta = Intent(this@MainActivity, insta::class.java)
                startActivity(insta)
                // Rewarded Ad
/*
                mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad was shown.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        // Called when ad fails to show.
                        Log.d(TAG, "Ad failed to show.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad was dismissed.")
                        mRewardedAd = null
                    }
                }
                if (mRewardedAd != null) {
                    val activityContext: Activity = this@MainActivity
                    mRewardedAd!!.show(
                        activityContext
                    ) { rewardItem -> // Handle the reward.
                        Log.d(TAG, "The user earned the reward.")
                        val rewardAmount = rewardItem.amount
                        val rewardType = rewardItem.type
                    }
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.")
                }
*/
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //exit app dialog box
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this, R.style.alertDialagTheme)
        builder.setTitle("Exit App")
        builder.setIcon(R.drawable.icon_warning)
        builder.setMessage("Are you sure, you want to exit..?")

        builder.setPositiveButton("Yes") { dialog, which ->
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
    }
    // rewarded ad function
    /*@Override
    fun videoadsshow(view: android.view.View) {
        *//*if (mRewardedAd != null) {
              val activityContext: Activity = this@MainActivity
              mRewardedAd!!.show(
                  activityContext
              ) { rewardItem -> // Handle the reward.
                  Log.d(TAG, "The user earned the reward.")
                  val rewardAmount = rewardItem.amount
                  val rewardType = rewardItem.type
              }
          } else {
              Log.d(TAG, "The rewarded ad wasn't ready yet.")
          }*//*
      }*/
}