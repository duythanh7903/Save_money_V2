package com.save_money.ver_two.ui.main

import android.widget.ImageView
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val listIconBottomBar: MutableList<ImageView> by lazy {
        mutableListOf(
            binding.iconHome,
            binding.iconChart,
            binding.iconAccount
        )
    }
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(this)
    }

    override fun initView() {
        initVpg()
        initTabDefault()
        clickViews()
    }

    private fun initVpg() =
        binding.vpg.apply {
            adapter = mainAdapter
            isUserInputEnabled = false
        }

    private fun clickViews() = binding.apply {
        btnHome.click { changeTab(0) }
        btnChart.click { changeTab(1) }
        btnAccount.click { changeTab(2) }
    }

    private fun changeTab(index: Int) {
        for (i in listIconBottomBar.indices) {
            listIconBottomBar[i].isActivated = false
        }
        listIconBottomBar[index].isActivated = true
        binding.vpg.currentItem = index
    }

    private fun initTabDefault() = changeTab(0)
}