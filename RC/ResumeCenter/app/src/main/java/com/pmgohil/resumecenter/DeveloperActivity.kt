package com.pmgohil.resumecenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class DeveloperActivity : AppCompatActivity() {

    lateinit var facebook: RelativeLayout
    lateinit var instagram: RelativeLayout
    lateinit var linkedin: RelativeLayout
    lateinit var mail: RelativeLayout
    lateinit var telegram: RelativeLayout
    lateinit var whatsapp: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        facebook = findViewById(R.id.fbRL)
        facebook.setOnClickListener() {
            clickOnFacebook()
        }
        instagram = findViewById(R.id.instaRL)
        instagram.setOnClickListener() {
            clickOnInstagram()
        }
        linkedin = findViewById(R.id.linkedinRL)
        linkedin.setOnClickListener() {
            clickOnLinkedin()
        }
        mail = findViewById(R.id.mailRL)
        mail.setOnClickListener() {
            clickOnMail()
        }
        telegram = findViewById(R.id.telegramRL)
        telegram.setOnClickListener() {
            clickOnTelegram()
        }
        whatsapp = findViewById(R.id.whatsappRL)
        whatsapp.setOnClickListener() {
            clickOnWhatsapp()
        }
    }

    fun clickOnFacebook() {
        val uri: Uri = Uri.parse(String.format("https://www.facebook.com/pm.gohil.4545"))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
        this.startActivity(intent)
    }

    fun clickOnInstagram() {
        val uri: Uri = Uri.parse(String.format("https://www.instagram.com/pmgohil45/"))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
        this.startActivity(intent)
    }

    fun clickOnLinkedin() {
        val uri: Uri = Uri.parse(String.format("https://www.linkedin.com/in/pm-gohil-1554aa240/"))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
        this.startActivity(intent)
    }

    fun clickOnMail() {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "pmgohil45@gmail.com", null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text")
        this.startActivity(Intent.createChooser(emailIntent, null))

    }

    fun clickOnTelegram() {
        val uri: Uri = Uri.parse(String.format("https://t.me/pm_gohil45"))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
        this.startActivity(intent)
    }

    fun clickOnWhatsapp() {
        val num: String = "+919512240793"
        val uri: Uri = Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", num))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
        this.startActivity(intent)
    }
}