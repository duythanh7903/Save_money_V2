package com.save_money.ver_two.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.save_money.ver_two.database.entities.Activities

@Dao
interface ActivitiesDao {
    @Insert
    fun insertActivity(activity: Activities)

    @Query("SELECT * FROM ACTIVITIES ORDER BY createdAt DESC")
    fun getAllActivities(): LiveData<MutableList<Activities>>
}