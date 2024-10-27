package com.save_money.ver_two.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.domain.user_repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    private val isLoading: LiveData<Boolean> = _isLoading

    fun searchUserByEmailAndPassword(
        email: String,
        password: String,
        onFound: (user: UserEntity) -> Unit,
        onNotFound: () -> Unit,
        onShowMessageFound: () -> Unit,
        onShowMessageNotFound: () -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.postValue(true)
        val result = repository.loginUserByEmailAndPassword(
            email = email,
            password = password
        )
        result?.let {
            onFound.invoke(it)
            withContext(Dispatchers.Main) {
                onShowMessageFound.invoke()
            }
        } ?: kotlin.run {
            onNotFound.invoke()
            withContext(Dispatchers.Main) {
                onShowMessageNotFound.invoke()
            }
        }
        _isLoading.postValue(false)
    }

}