package com.save_money.ver_two.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CHILD_CATEGORY")
data class ChildCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var categoryName: String = "",
    var iconCategoryChild: String = "",
    var categoryParent: ParentCategoryEntity = ParentCategoryEntity(),
    var createdAt: Long = System.currentTimeMillis()
) {
}