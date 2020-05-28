package com.example.android_week4

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter (fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->{
                NowPlaying()
            }
            1 ->{
                TopRating()
            }
            else ->{
                return MyFavorite()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}