package com.save_money.ver_two.domain.user_repo

import com.save_money.ver_two.database.dao.UserEntityDao
import com.save_money.ver_two.database.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor (
    private val dao: UserEntityDao
) {

    suspend fun saveAccountUse(user: UserEntity) =
        dao.saveAccountUser(user)

    suspend fun loginUserByEmailAndPassword(email: String, password: String) =
        dao.loginUserByEmailAndPassword(email, password)

    suspend fun updateAccountUser(user: UserEntity) =
        dao.updateAccountUser(user)

    suspend fun deleteAccountUser(user: UserEntity) =
        dao.deleteAccountUser(user)

}