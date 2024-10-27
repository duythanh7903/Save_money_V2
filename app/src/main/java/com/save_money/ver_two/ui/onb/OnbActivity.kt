package com.save_money.ver_two.ui.onb

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.databinding.ActivityOnbBinding
import com.save_money.ver_two.ui.login.LoginActivity
import com.save_money.ver_two.ui.onb.OnbUtils.getListOnb
import kotlin.math.abs

class OnbActivity: BaseActivity<ActivityOnbBinding>(R.layout.activity_onb) {

    private lateinit var onbAdapter: OnbAdapter
    private lateinit var compositePageTransformer: CompositePageTransformer
    private lateinit var listIndicatorView: MutableList<ImageView>

    private var posViewPager = 0

    override fun initView() {
        initVpg()
        clickViews()
    }

    private fun initVpg() = binding.vpg2.apply {
        onbAdapter = OnbAdapter().apply {
            submitData(getListOnb())
        }
        compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(100))
            addTransformer { view, position ->
                val r = 1 - abs(position)
                view.scaleY = 0.8f + r * 0.2f
                val absPosition = abs(position)
                view.alpha = 1.0f - (1.0f - 0.3f) * absPosition
            }
        }
        listIndicatorView = mutableListOf<ImageView>().apply {
            add(binding.indicator1)
            add(binding.indicator2)
            add(binding.indicator3)
        }

        adapter = onbAdapter
        clipToPadding = false
        clipChildren = false
        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        setPageTransformer(compositePageTransformer)
        registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            @SuppressLint("InvalidAnalyticsName", "UseCompatLoadingForDrawables")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                posViewPager = position
                updateIndicatorView(
                    indexSelected = position,
                    indicators = listIndicatorView
                )
            }
        })
    }

    fun updateIndicatorView(indexSelected: Int, indicators: MutableList<ImageView>) =
        indicators.forEachIndexed { index, imageView ->
            imageView.setImageResource(if (index == indexSelected) R.drawable.ic_ob_selected else R.drawable.ic_ob_un_selected)
        }

    private fun clickViews() = binding.apply {
        textNext.click {
            if (vpg2.currentItem < 2) vpg2.currentItem++ else gotoNextScreen()
        }

        textSkip.click { gotoNextScreen() }
    }

    private fun gotoNextScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}