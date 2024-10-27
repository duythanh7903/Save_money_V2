package com.save_money.ver_two.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER_ENTITY")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var email: String = "",
    var password: String = "",
    var userName: String = "",
    var base64Image: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = createdAt
) {
}