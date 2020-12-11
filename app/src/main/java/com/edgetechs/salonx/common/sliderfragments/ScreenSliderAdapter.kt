package com.edgetechs.salonx.common.sliderfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.edgetechs.salonx.R

class ScreenSliderAdapter : AppCompatActivity() {
    lateinit var viewPager : ViewPager
    lateinit var pageAdapter: ScreenSlidePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_slider_adapter)
        viewPager = findViewById(R.id.pager)
        if(viewPager!=null)
        { pageAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
            viewPager.adapter = pageAdapter}

    }
    inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 4

        override fun getItem(position: Int): Fragment = when(position){
            0-> fragment_getStarted()
            1-> fragment_reach()
            2-> fragment_customer()
            3-> fragment_brand()
            else -> fragment_getStarted()
        }

    }

}