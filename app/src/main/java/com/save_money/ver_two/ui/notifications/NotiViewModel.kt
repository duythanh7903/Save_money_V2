package com.save_money.ver_two.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.save_money.ver_two.database.entities.NotificationEntity
import com.save_money.ver_two.domain.notification_repo.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor (
    private val notiRepo: NotificationRepository
): ViewModel() {

    val listNoti =
        notiRepo.getAllNotifications()

    fun updateNoti(noti: NotificationEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            notiRepo.updateNotification(noti)
            cancel()
        }

    fun updateAllNoti(notis: MutableList<NotificationEntity>) =
        viewModelScope.launch(Dispatchers.IO) {
            notiRepo.updateAllNotifications(notis)
            cancel()
        }

}