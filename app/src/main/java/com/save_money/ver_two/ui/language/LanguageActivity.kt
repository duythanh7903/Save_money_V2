package com.save_money.ver_two.ui.language

import android.content.Intent
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.PreferencesUtils
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.showToast
import com.save_money.ver_two.databinding.ActivityLanguageBinding
import com.save_money.ver_two.ui.language.LanguageUtil.getListLanguages
import com.save_money.ver_two.ui.onb.OnbActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>(R.layout.activity_language) {

    private val languageAdapter: LanguageAdapter by lazy {
        LanguageAdapter(this@LanguageActivity) { languageSelected, index ->
            languageModel = languageSelected
            this.languageAdapter.apply {
                val indexBefore = indexSelected
                indexSelected = index
                notifyItemChanged(indexBefore)
            }
        }.apply {
            submitData(getListLanguages())
        }
    }

    private var languageModel: LanguageModel? = null

    override fun initView() {
        initRcvLanguage()
        clickViews()
    }

    private fun initRcvLanguage() = binding.rcvLanguage.apply {
        adapter = languageAdapter
    }

    private fun clickViews() = binding.apply {
        iconNext.click {
            languageModel?.let {
                setLocale(this@LanguageActivity, it.code)
                PreferencesUtils.languageCode = it.code
                startActivity(Intent(this@LanguageActivity, OnbActivity::class.java))
                finish()
            }
                ?: showToast(R.string.language)
        }
    }
}