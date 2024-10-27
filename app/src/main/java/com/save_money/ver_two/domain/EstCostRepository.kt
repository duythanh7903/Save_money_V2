package com.save_money.ver_two.domain

import com.save_money.ver_two.database.dao.RecordEstimateCostDao
import com.save_money.ver_two.database.entities.RecordEstimateCost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstCostRepository @Inject constructor (
    private val dao: RecordEstimateCostDao
) {

    suspend fun insertRecordEstimateCost(recordEstimateCost: RecordEstimateCost) =
        withContext(Dispatchers.IO) {
            dao.insertRecordEstimateCost(recordEstimateCost)
        }

    fun getAllRecordEstimateCosts() =
        dao.getAllRecordEstimateCosts()

    fun getCostsLast12Months() =
        dao.getCostsLast12Months()
}