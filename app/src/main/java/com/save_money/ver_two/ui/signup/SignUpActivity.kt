package com.save_money.ver_two.ui.signup

import androidx.activity.viewModels
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.showToast
import com.save_money.ver_two.commons.base.ext.validateEmail
import com.save_money.ver_two.commons.base.ext.validatePassword
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun initView() {
        clickViews()
    }

    private fun clickViews() = binding.apply {
        iconBack.click { finish() }
        btnSignUp.click { onSignUp() }
    }

    private fun onSignUp() {
        val email = binding.edtEmail.text.toString().trim()
        val userName = binding.edtUserName.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val isNullInput = isInputEmpty(email, userName, password)
        val isEmail = validateEmail(email)
        val isPassword = validatePassword(password)

        if (isNullInput) {
            showToast(R.string.this_field_cannot_be_left_blank)
            return
        }; if (!isEmail) {
            showToast(R.string.invalid_email)
            return
        }; if (!isPassword) {
            showToast(R.string.password_minimum_eight_characters)
            return
        }

        val userAccount = UserEntity(
            id = 0L,
            email = email,
            password = password,
            userName = userName
        )
        onRegisterAccount(userAccount)
    }

    private fun isInputEmpty(email: String, userName: String, password: String) =
        email.isEmpty() || userName.isEmpty() || password.isEmpty()

    private fun onRegisterAccount(user: UserEntity) =
        viewModel.searchUserByEmailAndPassword(
            email = user.email,
            password = user.password,
            onFound = { },
            onNotFound = {
                handleCreateAccount(user)
            },
            onShowMessageSuccess = {
                showToast(R.string.register_success)
                clearInput()
            },
            onShowMessageError = {
                showToast(R.string.email_already_active)
            }
        )

    private fun handleCreateAccount(user: UserEntity) =
        viewModel.createAccount(user)

    private fun clearInput() = binding.apply {
        edtEmail.text.clear()
        edtUserName.text.clear()
        edtPassword.text.clear()
    }
}