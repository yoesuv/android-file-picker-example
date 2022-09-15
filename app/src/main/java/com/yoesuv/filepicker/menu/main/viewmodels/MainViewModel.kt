package com.yoesuv.filepicker.menu.main.viewmodels

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.menu.camera.views.CameraActivity
import com.yoesuv.filepicker.menu.download.views.DownloadActivity
import com.yoesuv.filepicker.menu.file.views.FileActivity
import com.yoesuv.filepicker.menu.gallery.views.GalleryActivity
import com.yoesuv.filepicker.menu.location.views.LocationActivity

class MainViewModel: ViewModel() {

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

}