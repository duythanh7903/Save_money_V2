package com.save_money.ver_two

import android.app.Application
import com.save_money.ver_two.commons.PreferencesUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApp: Application() {

    override fun onCreate() {
        super.onCreate()

        PreferencesUtils.init(this)
    }

}