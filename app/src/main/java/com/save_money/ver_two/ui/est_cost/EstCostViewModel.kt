package com.save_money.ver_two.ui.est_cost

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.commons.PreferencesUtils
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.database.entities.RecordEstimateCost
import com.save_money.ver_two.domain.EstCostRepository
import com.save_money.ver_two.domain.activities_repo.ActivitiesRepository
import com.save_money.ver_two.domain.child_cat_repo.ChildCatRepository
import com.save_money.ver_two.domain.notification_repo.NotificationRepository
import com.save_money.ver_two.domain.parent_cat_repo.ParentCatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EstCostViewModel @Inject constructor(
    private val childCatRepo: ChildCatRepository,
    private val parentCatRepo: ParentCatRepository,
    private val costRepository: EstCostRepository,
    private val notiRepo: NotificationRepository,
    private val activityRepo: ActivitiesRepository
) : ViewModel() {

    val childCat = childCatRepo.getAllChildCategories()

    val parentCat = parentCatRepo.listParentCate

    val listCost =
        costRepository.getAllRecordEstimateCosts()

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

    fun saveRecordActualCost(record: RecordEstimateCost, context: Context) =
        viewModelScope.launch(Dispatchers.IO) {
            costRepository.insertRecordEstimateCost(record)
            PreferencesUtils.accountBalance -= record.cost
            cancel()
        }

    fun handleFilterRecordByDate(
        startDate: Long,
        endDate: Long,
        listRecord: MutableList<RecordEstimateCost>,
        onFinishFilter: (listRecord: MutableList<RecordEstimateCost>) -> Unit
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