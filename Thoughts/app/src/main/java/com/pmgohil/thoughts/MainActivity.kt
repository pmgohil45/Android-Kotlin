package com.pmgohil.thoughts

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(), MyThoughtsMenuAdapterCallback {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer_layout: DrawerLayout
    lateinit var navigation_view: NavigationView
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mFirebaseAuth: FirebaseAuth
    lateinit var menuRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var thoughtsDataAdapter: ThoughtsDataAdapter
    private lateinit var storageReference: StorageReference
    private lateinit var imageList: MutableList<ImageModel>
    private lateinit var userDataList: MutableList<UserData>
    private lateinit var thoughtsList: MutableList<ThoughtsDataModel>
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private var menuItemList = listOf(
        //MenuItem("all", "All"),
        MenuItem("days_special", "Days Special"),
        MenuItem("motivational", "Motivational"),
        MenuItem("attitude", "Attitude"),
        MenuItem("birthday", "Birthday"),
        MenuItem("love", "Love"),
        MenuItem("happy", "Happy"),
        MenuItem("sad", "Sad"),
        MenuItem("coders", "Coders"),
        MenuItem("buddha", "Buddha"),
        MenuItem("chanakya", "Chanakya"),
        MenuItem("samrat_ashoka", "Samrat Ashoka"),
        MenuItem("maharana_pratap", "Maharana Pratap"),
        MenuItem("chhatrapati_shivaji_maharaj", "Chhatrapati Shivaji Maharaj"),
        MenuItem("chhatrapati_sambhaji_maharaj", "Chhatrapati Sambhaji Maharaj"),
        MenuItem("swami_vivekananda", "Swami Vivekananda"),
        MenuItem("guru_gobind_singh", "Guru Gobind Singh"),
        MenuItem("b_r_ambedkar", "B R Ambedkar"),
        MenuItem("subhas_chandra_bose", "Subhas Chandra Bose"),
        MenuItem("sardar_vallabhbhai_patel", "Sardar Vallabhbhai Patel"),
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.transparent))

        /************************** Hooks with XML Elements **************************/
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        drawer_layout = findViewById(R.id.drawer_layout)
        navigation_view = findViewById(R.id.navigation_view)
        recyclerView = findViewById(R.id.recyclerView)
        menuRecyclerView = findViewById(R.id.menuRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        sharedPreferencesManager = SharedPreferencesManager(this)
        GlobalVariableFunction.name = sharedPreferencesManager.name.toString()
        GlobalVariableFunction.user_name = sharedPreferencesManager.username.toString()
        GlobalVariableFunction.email = sharedPreferencesManager.email.toString()
        GlobalVariableFunction.internal_image = sharedPreferencesManager.imageUrl.toString()
        GlobalVariableFunction.mail_id_image = sharedPreferencesManager.imageUrl.toString()

        try {
            if (GlobalVariableFunction.name.isNotEmpty() && GlobalVariableFunction.user_name.isNotEmpty() && GlobalVariableFunction.email.isNotEmpty() && GlobalVariableFunction.internal_image.isNotEmpty() && GlobalVariableFunction.mail_id_image.isNotEmpty()) {

                /************************** Click Listener on Elements **************************/
                swipeRefreshLayout.setOnRefreshListener {
                    swipeRefreshLayout.postDelayed({
                        select_users_data_from_firebase_database()
                        swipeRefreshLayout.isRefreshing = false
                    }, 2000)
                }

                /************************** Action Bar Code **************************/
                toggle = ActionBarDrawerToggle(
                    this,
                    drawer_layout,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
                )
                drawer_layout.addDrawerListener(toggle)
                toggle.syncState()
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                /************************** Navigation Menu Click Events **************************/
                navigation_view.setNavigationItemSelectedListener {
                    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        drawer_layout.closeDrawer(GravityCompat.START)
                    }
                    when (it.itemId) {
                        R.id.menu_profile -> profile()
                        R.id.menu_logout -> logout_from_thoughts_app()
                        R.id.menu_share -> socialMediaSharing()
                        R.id.menu_rate -> ratingInPlayStore()
                        R.id.menu_privacy_policy -> privacy_policy()
                    }
                    true
                }

                val headerView = navigation_view.getHeaderView(0)
                val navigation_header_link_txt: TextView =
                    headerView.findViewById(R.id.navigation_header_link_txt)

                navigation_header_link_txt.setOnClickListener {
                    val url = "https://www.codeimagin.com/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                /************************** Function calls **************************/
                firebase_database_connection()
                select_users_data_from_firebase_database()
                thoughts_menu_adapter_call()
                show_thought_data_in_recycler_view()

            } else {
                Firebase.auth.signOut()
                sharedPreferencesManager.clear()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        } catch (e: Exception) {
            Firebase.auth.signOut()
            sharedPreferencesManager.clear()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun thoughts_menu_adapter_call() {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        menuRecyclerView.layoutManager = layoutManager

        val adapter = ThoughtsMenuAdapter(this, menuItemList, this)
        menuRecyclerView.adapter = adapter
    }

    override fun onButtonClick(thoughtsMenuItemId: String) {
        select_thoughts_data_from_firebase_database_according_to_menu_item(thoughtsMenuItemId)
    }

    private fun select_thoughts_data_from_firebase_database_according_to_menu_item(key_menuId: String) {
        progressBar.visibility = View.VISIBLE
        imageList = mutableListOf()
        userDataList = mutableListOf()
        thoughtsList = mutableListOf()

        try {
            databaseReference = FirebaseDatabase.getInstance().reference.child("thoughts_data")
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    thoughtsList.clear()
                    for (thoughtsSnapshot in snapshot.children) {
                        thoughtsList.clear()
                        for (thoughtsSnapshot in snapshot.children) {
                            val thoughts_select =
                                thoughtsSnapshot.child("thoughts").getValue(String::class.java)
                            val key = thoughtsSnapshot.key
                            val firstWordKey = extractFirstWord(key.toString())
                            if (firstWordKey == key_menuId) {
                                thoughts_select?.let {
                                    val thoughtModel = ThoughtsDataModel(thoughts = it)
                                    thoughtsList.add(thoughtModel)
                                }
                                select_thoughts_image_data_from_firebase_database()
                            }
                        }

                    }
                    progressBar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    GlobalVariableFunction.showToast(this@MainActivity, error.toString())
                }
            })
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

    private fun extractFirstWord(key: String): String {
        // Split the key based on a separator (e.g., "_", "-", or space) and return the first word
        return key.split(" ").firstOrNull() ?: ""
    }

    private fun profile() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

    private fun show_thought_data_in_recycler_view() {
        //load_lottie_animation.visibility = View.VISIBLE
        /*        Handler(Looper.getMainLooper()).postDelayed({
                    load_lottie_animation.visibility = View.VISIBLE
                    load_lottie_animation.playAnimation()
                }, 10000)*/
        progressBar.visibility = View.VISIBLE
        imageList = mutableListOf()
        userDataList = mutableListOf()
        thoughtsList = mutableListOf()

        select_thoughts_data_from_firebase_database()
    }

    private fun select_thoughts_data_from_firebase_database() {

        try {
            //quotes_api_call()
            databaseReference = FirebaseDatabase.getInstance().reference.child("thoughts_data")
            databaseReference.addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    thoughtsList.clear()
                    for (thoughtsSnapshot in snapshot.children) {
                        val thoughts_select =
                            thoughtsSnapshot.child("thoughts").getValue(String::class.java)
                        thoughts_select?.let {
                            val thoughtModel = ThoughtsDataModel(thoughts = it)
                            thoughtsList.add(thoughtModel)
                        }
                        select_thoughts_image_data_from_firebase_database()
                    }
                    progressBar.visibility = View.GONE

                }

                override fun onCancelled(error: DatabaseError) {
                    GlobalVariableFunction.showToast(this@MainActivity, error.toString())
                }
            })

        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

    private fun quotes_api_call() {
        val url =
            URL("https://api.api-ninjas.com/v1/quotes?category=happiness")
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty("accept", "application/json")

        val responseStream: InputStream = connection.inputStream
        val mapper = ObjectMapper()
        val root: JsonNode = mapper.readTree(responseStream)
        println(root.path("fact").asText())

    }

    private fun select_thoughts_image_data_from_firebase_database() {
        try {
            storageReference = FirebaseStorage.getInstance().reference.child("images")
            val listTask = storageReference.listAll()
            listTask.addOnSuccessListener { result ->
                for (item in result.items) {
                    item.downloadUrl.addOnSuccessListener { downloadUrl ->
                        val imageModel = ImageModel(downloadUrl.toString())
                        imageList.add(imageModel)
                        //thoughtsDataAdapter.notifyDataSetChanged()
                    }.addOnFailureListener { exception ->
                        GlobalVariableFunction.showToast(this, exception.toString())
                    }
                }
                setUpRecyclerView()
            }.addOnFailureListener { exception ->
                GlobalVariableFunction.showToast(this, exception.toString())
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

    private fun setUpRecyclerView() {
        if (thoughtsList.isNotEmpty() || imageList.isNotEmpty()) {
            thoughtsDataAdapter = ThoughtsDataAdapter(this, thoughtsList, imageList)
            recyclerView.adapter = thoughtsDataAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            GlobalVariableFunction.showToast(this, "No data available.")
        }
    }

    private fun select_users_data_from_firebase_database() {
        if (GlobalVariableFunction.user_name.isNotEmpty()) {
            databaseReference.child("users").child(GlobalVariableFunction.user_name).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val name = it.child("name").value.toString()
                        val mail_id_image = it.child("mail_id_image").value.toString()
                        val internal_image = it.child("internal_image").value.toString()

                        GlobalVariableFunction.name = name
                        GlobalVariableFunction.mail_id_image = mail_id_image
                        GlobalVariableFunction.internal_image = internal_image
                    }
                }.addOnFailureListener {
                    GlobalVariableFunction.showToast(this, "Failed")
                }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        show_app_leave_dialog_box()
    }

    /************************** Menu Item Select Default Method **************************/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun firebase_database_connection() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    fun logout_from_thoughts_app() {
        googleSignInClient.signOut().addOnCompleteListener {
            Firebase.auth.signOut()
            sharedPreferencesManager.clear()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun show_app_leave_dialog_box() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit").setMessage("Are you sure you want to leave?")
        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            GlobalVariableFunction.showToast(this, "Yes, I want to stay.")
        }
        val dialog = builder.create()
        dialog.show()
    }

    // share app on social media
    fun socialMediaSharing() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "\uD83C\uDFA8 Art of Thoughts \uD83E\uDD14\n" +
                        "Discover the world of inspiration with \"Art of Thoughts\"\n" +
                        "\n" +
                        "\uD83D\uDCF1 Download now: https://pmgohil.site/"
            )
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "Art of Thoughts")
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

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

    fun privacy_policy() {
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        startActivity(goToMarket)
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://codeimagin.com/art_of_thoughts/art_of_thoughts_user_privacy_policy_terms_conditions.html")//$packageName <- set after id=

            )
        )
    }
}
