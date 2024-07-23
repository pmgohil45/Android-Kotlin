package com.example.calculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {


    private var toast: Toast? = null
    lateinit var binding: ActivityMainBinding
    private var backPressedTime = 0L
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.black))

        try {
            MobileAds.initialize(this)
            adView = findViewById(R.id.adView)
            // Load an ad
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

            var footerManage = findViewById<TextView>(R.id.footerTxt)
            footerManage.setOnClickListener {
                val url = "https://www.pmgohil.site/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                //footerManage()
            }
            var calculatorPolicy = findViewById<TextView>(R.id.calculator)
            calculatorPolicy.setOnClickListener {
                val url = "https://pm-kishanprivacypolicy.blogspot.com/2022/01/calculator.html"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                //calculatorPolicy()
            }

            binding.btnAc.setOnClickListener {
                val length = binding.inputText.length()
                if (length > 0) {
                    binding.inputText.text = ""
                    binding.outputText.text = ""
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Does not contain a digit in input area...!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btn0.setOnClickListener {
                binding.inputText.append("0")
            }
            binding.btn1.setOnClickListener {
                binding.inputText.append("1")
            }
            binding.btn2.setOnClickListener {
                binding.inputText.append("2")
            }
            binding.btn3.setOnClickListener {
                binding.inputText.append("3")
            }
            binding.btn4.setOnClickListener {
                binding.inputText.append("4")
            }
            binding.btn5.setOnClickListener {
                binding.inputText.append("5")
            }
            binding.btn6.setOnClickListener {
                binding.inputText.append("6")
            }
            binding.btn7.setOnClickListener {
                binding.inputText.append("7")
            }
            binding.btn8.setOnClickListener {
                binding.inputText.append("8")
            }
            binding.btn9.setOnClickListener {
                binding.inputText.append("9")
            }
            binding.btnPlus.setOnClickListener {
                val length = binding.inputText.length()
                if (length > 0) {
                    binding.inputText.append("+")
                } else {
                    cancel()
                    toast = Toast.makeText(
                        applicationContext, "Please,Enter some digits...!", Toast.LENGTH_SHORT
                    )
                    toast!!.show()
                }
            }
            binding.btnMinus.setOnClickListener {
                binding.inputText.append("-")
            }
            binding.btnMultiplication.setOnClickListener {
                val length = binding.inputText.length()
                if (length > 0) {
                    binding.inputText.append("*")
                } else {
                    cancel()
                    toast = Toast.makeText(
                        applicationContext, "Please, Enter some digits...!", Toast.LENGTH_SHORT
                    )
                    toast!!.show()
                }
            }
            binding.btnEndBracket.setOnClickListener {
                val length = binding.inputText.length()
                if (length > 0) {
                    binding.inputText.append(")")
                } else {
                    cancel()
                    toast = Toast.makeText(
                        applicationContext, "Please, Enter some digits...!", Toast.LENGTH_SHORT
                    )
                    toast!!.show()
                }
            }
            binding.btnStartBracket.setOnClickListener {
                binding.inputText.append("(")
            }
            binding.btnDivide.setOnClickListener {
                val length = binding.inputText.length()
                if (length > 0) {
                    binding.inputText.append("/")
                } else {
                    cancel()
                    toast = Toast.makeText(
                        applicationContext, "Please, Enter some digits...!", Toast.LENGTH_SHORT
                    )
                    toast!!.show()
                }
            }
            binding.btnDot.setOnClickListener {
                binding.inputText.append(".")
            }
            binding.btnBack.setOnClickListener {
                val length = binding.inputText.length()
                if (length > 0) {
                    binding.inputText.text = binding.inputText.text.subSequence(0, length - 1)
                }
            }
            binding.btnEqual.setOnClickListener {
                try {
                    val length = binding.inputText.length()
                    if (length > 0) {
                        val expression =
                            ExpressionBuilder(binding.inputText.text.toString()).build()
                        val result = expression.evaluate()
                        val longResult = result.toLong()
                        if (result == longResult.toDouble()) {
                            binding.outputText.text = longResult.toString()
                        } else {
                            binding.outputText.text = result.toString()
                        }
                    } else {
                        cancel()
                        toast = Toast.makeText(
                            applicationContext, "Please, Enter some digits...!", Toast.LENGTH_SHORT
                        )
                        toast!!.show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext, "Please, Enter a valid input...!", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
        }
    }

    fun footerManage() {
        val footer = Intent(this@MainActivity, footerManage::class.java)
        startActivity(footer)
    }

    fun calculatorPolicy() {
        val calculatorPolicy = Intent(this@MainActivity, calculatorPolicy::class.java)
        startActivity(calculatorPolicy)
    }

    private fun cancel() {
        if (toast != null) {
            toast?.cancel()
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            cancel()
            toast = Toast.makeText(applicationContext, "Double tap to back...!", Toast.LENGTH_SHORT)
            toast!!.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}