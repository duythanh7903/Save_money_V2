package com.save_money.ver_two.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.save_money.ver_two.database.entities.UserEntity

@Dao
interface UserEntityDao {

    @Insert
    suspend fun saveAccountUser(user: UserEntity)

    @Query("SELECT * FROM user_entity WHERE email == :email and password == :password LIMIT 1")
    suspend fun loginUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Update
    suspend fun updateAccountUser(user: UserEntity)

    @Delete
    suspend fun deleteAccountUser(user: UserEntity)

}