package com.save_money.ver_two.domain

import com.save_money.ver_two.database.dao.RecordActualCostDao
import com.save_money.ver_two.database.entities.RecordActualCost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ActualCostRepository @Inject constructor (
    private val dao: RecordActualCostDao
) {

    suspend fun saveRecordActualCost(record: RecordActualCost) =
        withContext(Dispatchers.IO) {
            dao.insertRecordActualCost(record)
        }

    fun getAllRecordActualCosts() =
        dao.getAllRecordActualCosts()

    fun getCostsLast12Months() =
        dao.getCostsLast12Months()
}