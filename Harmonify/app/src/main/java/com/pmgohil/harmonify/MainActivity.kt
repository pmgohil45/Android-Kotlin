package com.pmgohil.harmonify

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var currentFragmentId: Int = R.id.nav_home
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val clientId = "8080330159e34a65ba6eee8da2677757"
    private val clientSecret = "3ad90255caf948cbb583df47bf05c953"

    private val redirectUri = "https://pmgohil.site/"
    private val authorizationURI =
        "    https://accounts.spotify.com/authorize?client_id=$clientId&response_type=code&redirect_uri=$redirectUri&scope=user-library-read"

    private val REQUEST_CODE = 1337
    var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestWindowFeature(Window.FEATURE_NO_TITLE)/*window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/

        setContentView(R.layout.activity_main)/*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AudioFragment()).commit()
            navView.setCheckedItem(R.id.nav_home)
        }
        spotifyAuthRequest()
        //setupViewPager()
    }

    // Spotify Authorization Request
    private fun spotifyAuthRequest() {
        val request = AuthorizationRequest.Builder(
            clientId, AuthorizationResponse.Type.TOKEN, redirectUri
        ).setScopes(arrayOf("user-library-read")).build()
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)

        val response = AuthorizationClient.getResponse(REQUEST_CODE, intent)
        when (response.type) {
            AuthorizationResponse.Type.TOKEN -> {
                accessToken = response.accessToken
                // Use the access token to make API requests
                setupViewPager()
            }

            AuthorizationResponse.Type.ERROR -> {
                Toast.makeText(
                    this, "Login Error: ${response.error}", Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun setupViewPager() {

        val adapter = ViewPagerAdapter(this)//, accessToken!!)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Local Songs"
                1 -> "Online Songs"
                else -> ""
            }
        }.attach()
        // Set the tab text color using the custom color selector
        tabLayout.setTabTextColors(
            resources.getColor(R.color.white, theme),
            resources.getColor(R.color.highlighted_music_color, theme)
        )

        // Optionally, set the tab indicator color
        tabLayout.setSelectedTabIndicatorColor(
            resources.getColor(
                R.color.highlighted_music_color, theme
            )
        )

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if (item.itemId == currentFragmentId) {
            //Toast.makeText(this, "You are already on this page", Toast.LENGTH_SHORT).show()
        } else {
            currentFragmentId = item.itemId
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))/*                    supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, AudioFragment()).commit()*/
                }

                R.id.nav_library -> {
                    startActivity(Intent(this, LibraryActivity::class.java))
                }

                R.id.nav_playlists -> {
                    startActivity(Intent(this, PlayListActivity::class.java))
                }


                /*                R.id.nav_settings -> {
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, SettingsFragment())
                                        .commit()
                                }*/

                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }

                R.id.nav_logout -> {
                    //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                    logoutFromSpotify()
                }
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private inner class ViewPagerAdapter(fa: FragmentActivity, private val token: String) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AudioFragment()
                1 -> OnlineAudioFragment().newInstance(token)//GaanaOnlineAudioFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }

    private fun logoutFromSpotify() {
        getSharedPreferences("SPOTIFY", Context.MODE_PRIVATE).edit().remove("access_token").apply()
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        WebView(this).clearCache(true)
        Toast.makeText(this, "You Logged out from Harmonify App", Toast.LENGTH_SHORT).show()

        // Clear stored access token
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SPOTIFY", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.apply()

        // Clear WebView cookies
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()

        // Optional: reload the WebView to reflect changes
        val webView = WebView(this)
        webView.clearCache(true)
        Toast.makeText(this, "You Logged out from Harmonify App", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}