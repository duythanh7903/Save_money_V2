package com.save_money.ver_two.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.save_money.ver_two.database.dao.ActivitiesDao
import com.save_money.ver_two.database.dao.ChildCategoryDao
import com.save_money.ver_two.database.dao.NotificationDao
import com.save_money.ver_two.database.dao.ParentCategoryDao
import com.save_money.ver_two.database.dao.RecordActualCostDao
import com.save_money.ver_two.database.dao.RecordEstimateCostDao
import com.save_money.ver_two.database.dao.RecordExpectedIncomeDao
import com.save_money.ver_two.database.dao.RecordRealIncomeDao
import com.save_money.ver_two.database.dao.UserEntityDao
import com.save_money.ver_two.database.entities.Activities
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.database.entities.NotificationEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.database.entities.RecordActualCost
import com.save_money.ver_two.database.entities.RecordEstimateCost
import com.save_money.ver_two.database.entities.RecordExpectedIncome
import com.save_money.ver_two.database.entities.RecordRealIncome
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.database.ext.Converters

@Database(
    entities = [
        UserEntity::class, ParentCategoryEntity::class, ChildCategoryEntity::class,
        RecordRealIncome::class, NotificationEntity::class, Activities::class,
        RecordActualCost::class, RecordExpectedIncome::class, RecordEstimateCost::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userEntityDao(): UserEntityDao
    abstract fun parentCatDao(): ParentCategoryDao
    abstract fun childCatDao(): ChildCategoryDao
    abstract fun recordRealIncomeDao(): RecordRealIncomeDao
    abstract fun notificationDao(): NotificationDao
    abstract fun activitiesDao(): ActivitiesDao
    abstract fun recordEstimateCostDao(): RecordEstimateCostDao
    abstract fun recordActualCostDao(): RecordActualCostDao
    abstract fun recordExpectedIncomeDao(): RecordExpectedIncomeDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            synchronized(this) {
                if (instance != null) instance!!
                else {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "SaveMoney.db"
                    )
                        .allowMainThreadQueries().build()
                    instance!!
                }
            }
    }

}