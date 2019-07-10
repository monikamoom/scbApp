package com.codemobiles.scbauthen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.codemobiles.cmauthen.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, intent)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        // custom tabs เรียกจาก ui -> main -> SuccessActivity
        for (i in 0 until tabs.tabCount) {
            val tab: TabLayout.Tab? = tabs.getTabAt(i)
            tab!!.customView = sectionsPagerAdapter.getTabView(i)
        }


        //tabs.setupWithViewPager(viewPager)
    }
}