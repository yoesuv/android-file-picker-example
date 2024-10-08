package com.yoesuv.filepicker.menu.main.viewmodels

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.menu.camera.views.CameraActivity
import com.yoesuv.filepicker.menu.datetime.views.DateTimeActivity
import com.yoesuv.filepicker.menu.download.views.DownloadActivity
import com.yoesuv.filepicker.menu.file.views.FileActivity
import com.yoesuv.filepicker.menu.gallery.views.GalleryActivity
import com.yoesuv.filepicker.menu.location.views.LocationActivity
import com.yoesuv.filepicker.menu.notification.views.NotificationActivity
import com.yoesuv.filepicker.menu.record.views.RecordAudioActivity

class MainViewModel : ViewModel() {

    fun clickGallery(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, GalleryActivity::class.java))
    }

    fun clickCamera(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, CameraActivity::class.java))
    }

    fun clickFile(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, FileActivity::class.java))
    }

    fun clickLocation(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, LocationActivity::class.java))
    }

    fun clickDownload(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, DownloadActivity::class.java))
    }

    fun clickAudio(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, RecordAudioActivity::class.java))
    }

    fun clickNotification(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, NotificationActivity::class.java))
    }

    fun clickDateTime(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, DateTimeActivity::class.java))
    }

}