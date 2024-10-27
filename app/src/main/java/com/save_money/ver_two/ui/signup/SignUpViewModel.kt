package com.save_money.ver_two.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.domain.user_repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUserByEmailAndPassword(
        email: String,
        password: String,
        onFound: () -> Unit,
        onNotFound: () -> Unit,
        onShowMessageSuccess: () -> Unit,
        onShowMessageError: () -> Unit
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val result =
                repository.loginUserByEmailAndPassword(email, password)
            result?.let {
                onFound.invoke()
                withContext(Dispatchers.Main) {
                    onShowMessageError.invoke()
                }
            } ?: kotlin.run {
                onNotFound.invoke()
                withContext(Dispatchers.Main) {
                    onShowMessageSuccess.invoke()
                }
            }
            _isLoading.postValue(false)
            cancel()
        }

    fun createAccount(user: UserEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            repository.saveAccountUse(user)
            _isLoading.postValue(false)
            cancel()
        }

}