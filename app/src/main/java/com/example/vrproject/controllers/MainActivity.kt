package com.example.vrproject.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vrproject.R
import com.example.vrproject.Utils
import com.example.vrproject.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureViewPager()
        configureTabLayout()

        //Utils.createTestProfiles()
        //Utils.addMyselfTestProfile()
    }

    private fun configureViewPager() {
        val pagerAdapter = ViewPagerAdapter(this)

        activity_main_pager.adapter = pagerAdapter
        activity_main_pager.isUserInputEnabled = false // Disable swiping
        activity_main_pager.currentItem = 1
    }

    private fun configureTabLayout() {
        TabLayoutMediator(activity_main_tab_layout, activity_main_pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Account"
                    tab.setIcon(R.drawable.baseline_person_black_18dp)
                }
                1 -> {
                    tab.text = "Results"
                    tab.setIcon(R.drawable.baseline_auto_graph_black_18dp)
                }
                2 -> {
                    tab.text = "Contact"
                    tab.setIcon(R.drawable.baseline_contact_page_black_18dp)
                }
            }
        }.attach()
    }
}