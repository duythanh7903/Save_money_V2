package com.save_money.ver_two.domain

import com.save_money.ver_two.database.dao.RecordExpectedIncomeDao
import com.save_money.ver_two.database.entities.RecordExpectedIncome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpectedIncomeRepository @Inject constructor (
    private val dao: RecordExpectedIncomeDao
) {

    suspend fun saveRecordExpectedIncome(record: RecordExpectedIncome) =
        dao.saveRecordExpectedIncome(record)

    fun getAllRecordExpectedIncome() =
        dao.getAllRecordExpectedIncome()

    fun getRevenueLast12Months() =
        dao.getRevenueLast12Months()

}