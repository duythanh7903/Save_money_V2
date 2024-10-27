package com.save_money.ver_two.di

import android.content.Context
import com.save_money.ver_two.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context)

    @Provides
    fun userEntityDao(appDB: AppDatabase) = appDB.userEntityDao()

    @Provides
    fun parentCatDao(appDB: AppDatabase) = appDB.parentCatDao()

    @Provides
    fun childCatDao(appDB: AppDatabase) = appDB.childCatDao()

    @Provides
    fun recordRealIncomeDao(appDB: AppDatabase) = appDB.recordRealIncomeDao()

    @Provides
    fun notificationDao(appDB: AppDatabase) = appDB.notificationDao()

    @Provides
    fun activitiesDao(appDB: AppDatabase) = appDB.activitiesDao()

    @Provides
    fun estimateCostDao(appDB: AppDatabase) = appDB.recordEstimateCostDao()

    @Provides
    fun actualCostDao(appDB: AppDatabase) = appDB.recordActualCostDao()

    @Provides
    fun expectedIncomeDao(appDB: AppDatabase) = appDB.recordExpectedIncomeDao()

}