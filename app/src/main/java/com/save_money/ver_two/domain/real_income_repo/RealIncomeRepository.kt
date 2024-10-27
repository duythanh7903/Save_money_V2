package com.save_money.ver_two.domain.real_income_repo

import com.save_money.ver_two.database.dao.RecordRealIncomeDao
import com.save_money.ver_two.database.entities.RecordRealIncome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealIncomeRepository @Inject constructor(
    private val dao: RecordRealIncomeDao
) {

    suspend fun saveRecordRealIncome(record: RecordRealIncome) =
        withContext(Dispatchers.IO) {
            dao.saveRecordRealIncome(record)
        }

    fun getAllRecordsRealIncome() =
        dao.getAllRecordRealIncome()

    fun getCostsLast12Months() = dao.getRevenueLast12Months()

}