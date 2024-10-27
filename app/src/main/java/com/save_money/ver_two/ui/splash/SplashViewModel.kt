package com.save_money.ver_two.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.commons.AppConst.getAllParentCategory
import com.save_money.ver_two.domain.parent_cat_repo.ParentCatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: ParentCatRepository
): ViewModel() {

    val listParentCat = repo
        .listParentCate

    fun insertAllCategory() = viewModelScope.launch(Dispatchers.IO) {
        repo.insertAllCategoryParent(getAllParentCategory())
    }

}