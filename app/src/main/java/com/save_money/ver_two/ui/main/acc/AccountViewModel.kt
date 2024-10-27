package com.save_money.ver_two.ui.main.acc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.domain.user_repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    fun updateAccount(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateAccountUser(user)
    }

}