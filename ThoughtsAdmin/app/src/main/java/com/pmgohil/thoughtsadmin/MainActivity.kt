package com.pmgohil.thoughtsadmin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.transparent))

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        try {
            fetch_admin_user_data()/*
                        if (GlobalVariableFunction.admin_user_role == "Super Admin") {
                            admin_user_role_is_super_admin()
                        } else if (GlobalVariableFunction.admin_user_role == "Admin") {
                            admin_user_role_is_admin()
                        } else if (GlobalVariableFunction.admin_user_role == "Editor") {
                            admin_user_role_is_editor()
                        } else {
                            GlobalVariableFunction.showToast(
                                this,
                                GlobalVariableFunction.admin_user_role.toString()
                            )
                        }*/
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

    // Super Admin
    private fun admin_user_role_is_super_admin() {
        val pagerAdapter = SuperAdminPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Upload Image/Thought"
                1 -> "Thoughts"
                2 -> "Images"
                3 -> "Users"
                4 -> "Admin Request"
                5 -> "Admin Users"
                else -> ""
            }
        }.attach()
    }

    private inner class SuperAdminPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 6

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> UploadImageFragment()
                1 -> SelectThoughtsFragment()
                2 -> SelectImageFragment()
                3 -> UsersFragment()
                4 -> AdminRequestFragment()
                5 -> AdminUsersFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }

    // Admin
    private fun admin_user_role_is_admin() {
        val pagerAdapter = AdminPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Upload Image/Thought"
                1 -> "Thoughts"
                2 -> "Images"
                3 -> "Users"
                else -> ""
            }
        }.attach()
    }

    private inner class AdminPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> UploadImageFragment()
                1 -> SelectThoughtsFragment()
                2 -> SelectImageFragment()
                3 -> UsersFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }

    // Admin
    private fun admin_user_role_is_editor() {
        val pagerAdapter = EditorPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Upload Image/Thought"
                1 -> "Thoughts"
                2 -> "Images"
                else -> ""
            }
        }.attach()
    }

    private inner class EditorPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> UploadImageFragment()
                1 -> SelectThoughtsFragment()
                2 -> SelectImageFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }

    private fun fetch_admin_user_data() {
        val database = FirebaseDatabase.getInstance()
        val adminUsersRef = database.getReference("admin_user")
        try {
            // Find the user by username and update the role
            adminUsersRef.orderByChild("unm").equalTo(GlobalVariableFunction.admin_user_name)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val role = userSnapshot.child("role").getValue(String::class.java)

                                if (role == "Super Admin") {
                                    admin_user_role_is_super_admin()
                                    GlobalVariableFunction.admin_user_role = "Super Admin"
                                } else if (role == "Admin") {
                                    admin_user_role_is_admin()
                                    GlobalVariableFunction.admin_user_role = "Admin"
                                } else if (role == "Editor") {
                                    admin_user_role_is_editor()
                                    GlobalVariableFunction.admin_user_role = "Editor"
                                }
                            }
                        }
                    }

                    override fun onCancelled(e: DatabaseError) {
                        GlobalVariableFunction.showToast(this@MainActivity, e.toString())
                    }
                })
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

}