package com.codemobiles.cmauthen.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.codemobiles.scbauthen.R
import com.codemobiles.scbauthen.TabChartFragment
import com.codemobiles.scbauthen.TabJSONFragment
import kotlinx.android.synthetic.main.custom_tab_layout.view.*


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, val intent: Intent) :
    FragmentPagerAdapter(fm) {

    private val PAGES: Int = 2

    private val TAB_TITLES = arrayOf("JSON", "Chart")

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                TabJSONFragment()
            }

            else -> {
                TabChartFragment()
            }
        }
    }


    override fun getCount(): Int {
        return PAGES
    }

    //custom menu tab
    fun getTabView(position: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.custom_tab_layout, null).apply {
            title.text = TAB_TITLES[position]
            icon.setImageResource(R.drawable.ic_tab1)
        }
    }
}
