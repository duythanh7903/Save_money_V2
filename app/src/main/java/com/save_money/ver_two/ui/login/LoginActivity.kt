package com.save_money.ver_two.ui.login

import android.content.Intent
import androidx.activity.viewModels
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.PreferencesUtils
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.convertObjectToJson
import com.save_money.ver_two.commons.base.ext.showToast
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.databinding.ActivityLoginBinding
import com.save_money.ver_two.ui.main.MainActivity
import com.save_money.ver_two.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        clickViews()
    }

    private fun clickViews() = binding.apply {
        btnGetStarted.click {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        btnLogin.click {
            onLogin()
        }
    }

    private fun onLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        viewModel.searchUserByEmailAndPassword(
            email = email,
            password = password,
            onFound = { user ->
                cacheAccountTypeJsonToShared(user)
                goToNextScreen()
                finish()
            },
            onNotFound = {},
            onShowMessageFound = {},
            onShowMessageNotFound = {
                showToast(R.string.account_incorrect)
            }
        )
    }

    private fun cacheAccountTypeJsonToShared(account: UserEntity) {
        val json = convertObjectToJson(account)
        PreferencesUtils.jsonAccount = json
    }

    private fun goToNextScreen() =
        startActivity(Intent(
            this@LoginActivity,
            MainActivity::class.java
        ))
}