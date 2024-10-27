package com.save_money.ver_two.ui.main.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChartViewModel: ViewModel() {

    companion object {
        internal const val INDEX_TAB_TODAY = 0
        internal const val INDEX_TAB_LAST7 = 1
        internal const val INDEX_TAB_LAST30 = 2
    }

    private val _indexTab = MutableLiveData<Int>()
    val indexTab = _indexTab

    init {
        _indexTab.postValue(INDEX_TAB_TODAY)
    }

    fun changeTab(index: Int) = _indexTab.postValue(index)

}