package com.save_money.ver_two.domain.notification_repo

import com.save_money.ver_two.database.dao.NotificationDao
import com.save_money.ver_two.database.entities.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor (
    private val dao: NotificationDao
) {

    suspend fun saveNotification(notification: NotificationEntity) =
        withContext(Dispatchers.IO) {
            dao.saveNotification(notification)
        }

    suspend fun updateNotification(noti: NotificationEntity) =
        withContext(Dispatchers.IO) {
            dao.updateNotification(noti)
        }

    suspend fun updateAllNotifications(notis: MutableList<NotificationEntity>) =
        withContext(Dispatchers.IO) {
            dao.updateAllNotifications(notis)
        }

    fun getAllNotifications() =
        dao.getAllNotifications()

    fun countNotificationNotRead() =
        dao.countNotificationNotRead()

}