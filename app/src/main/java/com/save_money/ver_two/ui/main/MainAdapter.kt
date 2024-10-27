package com.save_money.ver_two.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.save_money.ver_two.ui.main.acc.AccFragment
import com.save_money.ver_two.ui.main.chart.ChartFragment
import com.save_money.ver_two.ui.main.home.HomeFragment

class MainAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment()
            1 -> ChartFragment()
            else -> AccFragment()
        }
}