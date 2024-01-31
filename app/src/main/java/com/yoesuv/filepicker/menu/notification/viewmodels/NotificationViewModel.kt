package com.yoesuv.filepicker.menu.notification.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.CHANNEL_ID_NORMAL
import com.yoesuv.filepicker.utils.isOreo

class NotificationViewModel : ViewModel() {

    private lateinit var nManager: NotificationManagerCompat
    fun init(context: Context) {
        nManager = NotificationManagerCompat.from(context)
        if (isOreo()) {
            val channel = NotificationChannelCompat.Builder(
                CHANNEL_ID_NORMAL,
                NotificationManagerCompat.IMPORTANCE_DEFAULT
            ).setName("normal notification channel")
            nManager.createNotificationChannel(channel.build())
        }
    }

    @SuppressLint("MissingPermission")
    fun normalNotification(context: Context) {
        val title = context.getString(R.string.button_normal_notification)
        val content = context.getString(R.string.lorem_ipsum_short)
        val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
            .setSmallIcon(R.drawable.ic_small_favorite_border)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        nManager.notify(1, nBuilder)
    }

}