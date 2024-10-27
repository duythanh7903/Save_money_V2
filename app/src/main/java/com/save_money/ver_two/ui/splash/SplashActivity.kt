package com.save_money.ver_two.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.PreferencesUtils
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.databinding.ActivitySplashBinding
import com.save_money.ver_two.ui.language.LanguageActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun initView() {

        PreferencesUtils.init(this)

        viewModel.listParentCat.observe(this) {
            if (it.isEmpty()) viewModel.insertAllCategory()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, LanguageActivity::class.java))
            finish()
        }, 5000)
    }
}