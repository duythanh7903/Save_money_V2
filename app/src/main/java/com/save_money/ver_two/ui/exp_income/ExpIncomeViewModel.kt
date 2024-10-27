package com.save_money.ver_two.ui.exp_income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.database.entities.RecordExpectedIncome
import com.save_money.ver_two.domain.ExpectedIncomeRepository
import com.save_money.ver_two.domain.child_cat_repo.ChildCatRepository
import com.save_money.ver_two.domain.parent_cat_repo.ParentCatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExpIncomeViewModel @Inject constructor(
    private val childCatRepo: ChildCatRepository,
    private val parentCatRepo: ParentCatRepository,
    private val expRepo: ExpectedIncomeRepository,
) : ViewModel() {

    val childCat = childCatRepo.getAllChildCategories()

    val parentCat = parentCatRepo.listParentCate

    val listRecordsExp =
        expRepo.getAllRecordExpectedIncome()

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

    fun saveRecordExpectedIncome(record: RecordExpectedIncome) =
        viewModelScope.launch(Dispatchers.IO) {
            expRepo.saveRecordExpectedIncome(record)
            cancel()
        }

    fun handleFilterRecordByDate(
        startDate: Long,
        endDate: Long,
        listRecord: MutableList<RecordExpectedIncome>,
        onFinishFilter: (listRecord: MutableList<RecordExpectedIncome>) -> Unit
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