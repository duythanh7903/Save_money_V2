package com.save_money.ver_two.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.save_money.ver_two.database.entities.RecordExpectedIncome
import com.save_money.ver_two.domain.model.MonthlyMoney

@Dao
interface RecordExpectedIncomeDao {

    @Insert
    suspend fun saveRecordExpectedIncome(record: RecordExpectedIncome)

    @Query("SELECT * FROM RECORD_EXPECTED_INCOME ORDER BY time DESC")
    fun getAllRecordExpectedIncome(): LiveData<MutableList<RecordExpectedIncome>>

    @Query("""
        SELECT strftime('%Y-%m', datetime(time / 1000, 'unixepoch')) AS monthYear, 
               SUM(revenue) as totalCost 
        FROM RECORD_EXPECTED_INCOME
        GROUP BY monthYear
        ORDER BY monthYear DESC
        LIMIT 12
    """)
    fun getRevenueLast12Months(): LiveData<MutableList<MonthlyMoney>>
}