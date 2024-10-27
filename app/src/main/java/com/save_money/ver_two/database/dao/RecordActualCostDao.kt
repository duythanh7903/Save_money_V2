package com.save_money.ver_two.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.save_money.ver_two.database.entities.RecordActualCost
import com.save_money.ver_two.domain.model.MonthlyMoney

@Dao
interface RecordActualCostDao {

    @Insert
    fun insertRecordActualCost(costs: RecordActualCost)

    @Query("SELECT * FROM RECORD_ACTUAL_COSTS ORDER BY time DESC")
    fun getAllRecordActualCosts(): LiveData<MutableList<RecordActualCost>>

    @Query("""
        SELECT strftime('%Y-%m', datetime(time / 1000, 'unixepoch')) AS monthYear, 
               SUM(cost) as totalCost 
        FROM RECORD_ACTUAL_COSTS
        GROUP BY monthYear
        ORDER BY monthYear DESC
        LIMIT 12
    """)
    fun getCostsLast12Months(): LiveData<MutableList<MonthlyMoney>>

}