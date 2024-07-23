package com.example.viewpager3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.text.FieldPosition

class frag1(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return fragment1()
            }
            1 -> {
                return fragment2()
            }
            2 -> {
                return fragment3()
            }
            else ->{
                return fragment1()
            }
        }
    }
}