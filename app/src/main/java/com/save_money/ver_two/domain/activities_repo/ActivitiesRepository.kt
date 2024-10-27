package com.save_money.ver_two.domain.activities_repo

import com.save_money.ver_two.database.dao.ActivitiesDao
import com.save_money.ver_two.database.entities.Activities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivitiesRepository @Inject constructor (
    private val dao: ActivitiesDao
) {

    suspend fun insertActivity(activity: Activities) =
        withContext(Dispatchers.IO) {
            dao.insertActivity(activity)
        }

    fun getAllActivities() = dao.getAllActivities()

}