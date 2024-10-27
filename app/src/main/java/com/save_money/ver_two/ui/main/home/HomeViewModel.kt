package com.save_money.ver_two.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.databinding.FragmentHomeBinding
import com.save_money.ver_two.domain.activities_repo.ActivitiesRepository
import com.save_money.ver_two.domain.notification_repo.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val actRepo: ActivitiesRepository,
): ViewModel() {

    val listActivities = actRepo.getAllActivities()

}