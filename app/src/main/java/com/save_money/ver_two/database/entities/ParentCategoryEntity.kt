package com.save_money.ver_two.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.save_money.ver_two.R

@Entity(tableName = "PARENT_CATEGORY")
data class ParentCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var categoryName: String = "",
    var imageRes: Int = R.drawable.dollar,
    var typeCategory: Int = TYPE_INCOME
) {

    companion object {
        internal const val TYPE_INCOME = 0
        internal const val TYPE_EXPENSE = 1
    }

}