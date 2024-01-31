package com.yoesuv.filepicker.menu.notification.viewmodels

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.CHANNEL_ID_NORMAL
import com.yoesuv.filepicker.data.CHANNEL_ID_SOUND
import com.yoesuv.filepicker.utils.isOreo
import java.io.File


class NotificationViewModel : ViewModel() {

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

        val nManager = NotificationManagerCompat.from(context)
        if (isOreo()) {
            val channel = NotificationChannelCompat.Builder(
                CHANNEL_ID_NORMAL,
                NotificationManagerCompat.IMPORTANCE_DEFAULT
            ).setName("normal notification channel")
            nManager.createNotificationChannel(channel.build())
        }
        nManager.notify(1, nBuilder)
    }

    @SuppressLint("MissingPermission")
    fun customSoundNotification(context: Context) {
        val title = context.getString(R.string.button_custom_sound_notification)
        val content = context.getString(R.string.lorem_ipsum_short)

        val scheme = ContentResolver.SCHEME_ANDROID_RESOURCE
        val res = scheme + "://" + context.packageName + File.separator
        val sound = Uri.parse(res + "raw/melodic_alert_tone")

        val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID_SOUND)
            .setSmallIcon(R.drawable.ic_small_music_note)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val nManager = NotificationManagerCompat.from(context)
        if (isOreo()) {
            val attrs = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            val channel = NotificationChannelCompat.Builder(
                CHANNEL_ID_SOUND,
                NotificationManagerCompat.IMPORTANCE_HIGH
            ).setName("custom sound notification channel")
            channel.setVibrationEnabled(true)
            channel.setVibrationPattern(longArrayOf(250, 250, 0, 500))
            channel.setSound(sound, attrs)
            channel.setLightsEnabled(true)

            nManager.createNotificationChannel(channel.build())
        }
        nManager.notify(2, nBuilder)
    }

}