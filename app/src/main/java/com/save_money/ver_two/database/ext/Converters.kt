package com.save_money.ver_two.database.ext

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity

class Converters {

    @TypeConverter
    fun fromParentCategory(categoryParent: ParentCategoryEntity): String {
        return Gson().toJson(categoryParent)
    }

    @TypeConverter
    fun toParentCategory(data: String): ParentCategoryEntity {
        return Gson().fromJson(data, ParentCategoryEntity::class.java)
    }

    @TypeConverter
    fun fromChildCategory(childCategory: ChildCategoryEntity): String {
        return Gson().toJson(childCategory)
    }

    @TypeConverter
    fun toChildCategory(data: String): ChildCategoryEntity {
        return Gson().fromJson(data, ChildCategoryEntity::class.java)
    }

}