package com.example.vrproject.adapters

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vrproject.fragments.AccountFragment
import com.example.vrproject.fragments.ContactFragment
import com.example.vrproject.fragments.ResultsFragment

class ViewPagerAdapter(@NonNull fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AccountFragment()
            }
            1 -> {
                ResultsFragment()
            }
            2 -> {
                ContactFragment()
            }
            else -> {
                AccountFragment()
            }
        }
    }
}