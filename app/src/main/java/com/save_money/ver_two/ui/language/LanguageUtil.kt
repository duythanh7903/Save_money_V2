package com.save_money.ver_two.ui.language

import com.save_money.ver_two.R

object LanguageUtil {

    fun getListLanguages() = mutableListOf(
        LanguageModel(0L, "vi", "VietNamese", R.drawable.vietnam),
        LanguageModel(0L, "en", "English", R.drawable.english),
        LanguageModel(0L, "hi", "Hindi", R.drawable.hindi),
        LanguageModel(0L, "pt", "Portugal", R.drawable.portugal),
    )

}