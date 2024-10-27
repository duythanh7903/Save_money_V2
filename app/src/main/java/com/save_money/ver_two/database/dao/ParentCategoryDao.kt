package com.save_money.ver_two.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.save_money.ver_two.database.entities.ParentCategoryEntity

@Dao
interface ParentCategoryDao {
    @Insert
    suspend fun insertAllCategoryParent(categories: MutableList<ParentCategoryEntity>)

    @Query("SELECT * FROM PARENT_CATEGORY")
    fun getAllCategoryParents(): LiveData<MutableList<ParentCategoryEntity>>
}