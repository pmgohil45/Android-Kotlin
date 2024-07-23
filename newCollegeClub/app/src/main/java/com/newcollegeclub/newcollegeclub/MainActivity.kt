package com.newcollegeclub.newcollegeclub

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView
    private val URL = "https://newcollegeclub.000webhostapp.com/"
    lateinit var webview: WebView
    lateinit var context: Context
    lateinit var activity: Activity
    lateinit var downloadListener: DownloadListener
    var writeAccess = false
    private val PERMISSION_REQUEST_CODE = 1234
    private val downloadPage = "https://newcollegeclub.000webhostapp.com/main.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        checkDownloadPermission()

        webView = findViewById(R.id.webView)
        webView.loadUrl(URL)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webView.setDownloadListener(object : DownloadListener {
            override fun onDownloadStart(
                url: String,
                userAgent: String,
                contentDisposition: String,
                mimetype: String,
                contentLength: Long
            ) {
                val request = DownloadManager.Request(Uri.parse(url))
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype))
                request.setDescription("Downloading file...")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    URLUtil.guessFileName(url, contentDisposition, mimetype)
                )
                val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(applicationContext, "Downloading...", Toast.LENGTH_SHORT).show()
                registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            }

            var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    Toast.makeText(applicationContext, "Downloading Complete", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun checkDownloadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this@MainActivity,
                "Write External Storage permission allows us to save files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }
}


/*
/** Application Context and Main Activity */
context = applicationContext
activity = this

/** Initialize main layout and web view */
webView = findViewById(R.id.webView)

/** Check permission to write in external storage */
checkWriteAccess()
/** Create a Download Listener */
createDownloadListener()
/** Display Toast Message When Download Complete */
onDownloadComplete()
/** Configure Web View */
configureWebView()

}

private fun onDownloadComplete() {
/**  Code that receives and handles broadcast intents sent by Context.sendBroadcast(Intent) */
val onComplete = object : BroadcastReceiver() {
    override fun onReceive(ctxt: Context, intent: Intent) {
        Toast.makeText(context, "File Downloaded", Toast.LENGTH_LONG).show()
    }
}

/** Register to receives above broadcast */
registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
}

@SuppressLint("SetJavaScriptEnabled")
private fun configureWebView() {

/** Web View General Setup */

/**
When user clicks on an URL the default behaviour is android open the default application
which handles URL. It means android will open a default browser. We need to handle this.
Why? Because we need to open the URL in the same web view. In our MyWebViewClient we
will override shouldOverrideUrlLoading function to again load the new url in our
web view.
 */
webView.webViewClient = MyWebViewClient()
/** getSettings() : Gets the WebSettings object used to control the settings for this WebView. */
/** We will use it to enable the Java Script Support. */
webView.settings.javaScriptEnabled = true
/** loadUrl : Loads the given URL. */
webView.loadUrl(downloadPage)

/** File Download Listener */
webView.setDownloadListener(downloadListener)
}


/**
* Custom WebViewClient to override URL Loading.
*/

private inner class MyWebViewClient : WebViewClient() {

/**
 * Override to open URL in WebView
 * */
override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
    view.loadUrl(url)
    return true
}

/**
 * Override to open URL in WebView
 * */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
    view.loadUrl(request.url.toString())
    return true
}
}


private fun createDownloadListener() {


/** A New Download Listener for our WebView */
downloadListener =
    DownloadListener { url, userAgent, contentDescription, mimetype, contentLength ->

        /**
         * This class contains all the information necessary to request a new download.
         * The URI is the only required parameter. Note that the default download destination
         * is a shared volume where the system might delete your file if it needs to reclaim
         * space for system use.
         * */
        val request = DownloadManager.Request(Uri.parse(url))

        /**
         * If the file to be downloaded is to be scanned by MediaScanner, this method should
         * be called before DownloadManager.enqueue(Request) is called.
         */
        request.allowScanningByMediaScanner()

        /**
         * Control whether a system notification is posted by the download manager while this
         * download is running or when it is completed.
         * */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        /**
         * Guesses canonical filename that a download would have, using the
         * URL and contentDisposition.
         * */
        val fileName = URLUtil.guessFileName(url, contentDescription, mimetype)

        /**
         * Set the local destination for the downloaded file to a path within the public
         * external storage directory (as returned by Environment.getExternalStoragePublicDirectory(String)).
         * */
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        /**
         * Get Download Manager Service
         * */
        val dManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        /**
         * Enqueue a new request to Download our File.
         * */

        if (writeAccess)
            dManager.enqueue(request)
        else {
            Toast.makeText(
                context,
                "Unable to download file. Required Privileges are not available.",
                Toast.LENGTH_LONG
            ).show()
            checkWriteAccess()
        }

    }
}

private fun checkWriteAccess() {
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    /**
     * Check for permission status.
     * */
    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Required permission to write external storage to save downloaded file.")
            builder.setTitle("Please Grant Write Permission")
            builder.setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
            builder.setNeutralButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    } else {
        /**
         * Already have required permission.
         * */
        writeAccess = false
    }
}
}

override fun onRequestPermissionsResult(
requestCode: Int,
permissions: Array<String>,
grantResults: IntArray
) {
super.onRequestPermissionsResult(requestCode, permissions, grantResults)
when (requestCode) {
    PERMISSION_REQUEST_CODE -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            writeAccess = true
        } else {
            // Permission denied
            writeAccess = false
            Toast.makeText(
                context,
                "Permission Denied. This app will not work with right permission.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
}
}

*/
/*
        //let's create out connection manager
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        //now let's check the connection and display the information in our layout

        if (isConnected) {
            webView = findViewById<WebView>(R.id.webView)
            webView.webViewClient = WebViewClient()
            webView.loadUrl("https://newcollegeclub.000webhostapp.com/")
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)
        } else {
            network()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    fun network() {
        val network = Intent(this, network::class.java)
        startActivity(network)
        finish()
    }
}
*/