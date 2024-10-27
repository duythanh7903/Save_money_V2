package com.save_money.ver_two.ui.real_income

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.AppConst.CHANEL_ID_NOTI
import com.save_money.ver_two.commons.AppConst.NOTI_ID
import com.save_money.ver_two.commons.PreferencesUtils
import com.save_money.ver_two.commons.base.ext.convertJsonToObject
import com.save_money.ver_two.commons.base.ext.formatNumberWithDots
import com.save_money.ver_two.database.entities.Activities
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.database.entities.NotificationEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.database.entities.RecordRealIncome
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.domain.activities_repo.ActivitiesRepository
import com.save_money.ver_two.domain.child_cat_repo.ChildCatRepository
import com.save_money.ver_two.domain.notification_repo.NotificationRepository
import com.save_money.ver_two.domain.parent_cat_repo.ParentCatRepository
import com.save_money.ver_two.domain.real_income_repo.RealIncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RealIncomeViewModel @Inject constructor(
    private val childCatRepo: ChildCatRepository,
    private val parentCatRepo: ParentCatRepository,
    private val realIncomeRepo: RealIncomeRepository,
    private val notiRepo: NotificationRepository,
    private val activityRepo: ActivitiesRepository
) : ViewModel() {

    val childCat = childCatRepo.getAllChildCategories()

    val parentCat = parentCatRepo.listParentCate

    val listRecordRevenue =
        realIncomeRepo.getAllRecordsRealIncome()

    private val _isFilter = MutableLiveData<Boolean>()
    val isFilter: LiveData<Boolean> = _isFilter

    init {
        _isFilter.value = false
    }


    fun updateChildCate(childCat: ChildCategoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        childCatRepo.updateChildCategory(childCat)
    }

    fun insertChildCate(catName: String, parentCate: ParentCategoryEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            val category = ChildCategoryEntity(
                0L, catName, "", parentCate
            )
            childCatRepo.insertChildCategory(category)
            cancel()
        }

    fun saveRecordRealIncome(record: RecordRealIncome, context: Context) =
        viewModelScope.launch(Dispatchers.IO) {
            realIncomeRepo.saveRecordRealIncome(record)
            PreferencesUtils.accountBalance += record.revenue
            saveActivitiesInsertRecordRealIncome(record)
            saveNotification(context, record)
            sendNotification(context, record)
            cancel()
        }

    private suspend fun saveActivitiesInsertRecordRealIncome(record: RecordRealIncome) {
        val activities = Activities(
            eventName = record.noteTitle,
            createdAt = record.time,
            balanceFluctuations = record.revenue,
            isBalanceVolatilityIncreasing = true,
            balanceAmount = PreferencesUtils.accountBalance
        )
        activityRepo.insertActivity(activities)
    }

    private fun sendNotification(context: Context, record: RecordRealIncome) {
        val notificationId = NOTI_ID
        val channelId = CHANEL_ID_NOTI

        // Tạo thông báo
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle(context.getString(R.string.balance_fluctuations))
            .setContentText(
                "${
                    context.getString(
                        R.string.amount_params,
                        context.convertJsonToObject(
                            PreferencesUtils.jsonAccount,
                            UserEntity::class.java
                        ).userName
                    )
                }\n${
                    context.getString(
                        R.string.transaction_amount_params,
                        record.revenue
                    )
                }\n${
                    context.getString(
                        R.string.balance_params,
                        context.formatNumberWithDots(PreferencesUtils.accountBalance)
                    )
                }"
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Gửi thông báo
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private suspend fun saveNotification(context: Context, record: RecordRealIncome) {
        val notification = NotificationEntity(
            id = 0L,
            title = "Balance fluctuations",
            content = "Amount ${
                context.convertJsonToObject(
                    PreferencesUtils.jsonAccount,
                    UserEntity::class.java
                ).userName
            }\nTransaction amount: +${record.revenue}\nBalance: ${PreferencesUtils.accountBalance}",
        )
        notiRepo.saveNotification(notification)
    }

    fun handleFilterRecordByDate(
        startDate: Long,
        endDate: Long,
        listRecord: MutableList<RecordRealIncome>,
        onFinishFilter: (listRecord: MutableList<RecordRealIncome>) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        _isFilter.postValue(true)
        val listFilter =
            listRecord.filter { it.time in startDate..endDate }.toMutableList()
        withContext(Dispatchers.Main) {
            onFinishFilter.invoke(listFilter)
        }
    }

    fun clearFilter() {
        _isFilter.postValue(false)
    }

}