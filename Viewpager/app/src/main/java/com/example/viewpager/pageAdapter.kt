package com.example.viewpager

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter

class pageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
//    override fun getCount(): Int {
//        return 3;
//    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        when (position) {
//            0 -> {
//                return "Tab 1"
//            }
//            1 -> {
//                return "Tab 2"
//            }
//            2 -> {
//                return "Tab 3"
//            }
//        }
//        return super.get(position)
//    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return Fragment1()
            }
            1 -> {
                return Fragment2()
            }
            2 -> {
                return Fragment3()
            }
            else -> {
                return Fragment1()
            }
        }
    }

}