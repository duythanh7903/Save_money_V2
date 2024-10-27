package com.save_money.ver_two.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.save_money.ver_two.database.entities.ChildCategoryEntity

@Entity(tableName = "RECORD_ESTIMATE_COSTS")
data class RecordEstimateCost(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var noteTitle: String = "",
    var cost: Long = 0,
    var time: Long = System.currentTimeMillis(),
    var categoryEstimateCost: ChildCategoryEntity = ChildCategoryEntity()
) {
}