package com.save_money.ver_two.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.save_money.ver_two.database.entities.ChildCategoryEntity

@Dao
interface ChildCategoryDao {

    @Insert
    suspend fun insertChildCategory(category: ChildCategoryEntity)

    @Query("SELECT * FROM CHILD_CATEGORY ORDER BY createdAt DESC")
    fun getAllChildCategories(): LiveData<MutableList<ChildCategoryEntity>>

    @Update
    suspend fun updateChildCategory(category: ChildCategoryEntity)

}