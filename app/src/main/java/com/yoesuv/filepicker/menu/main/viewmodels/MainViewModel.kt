package com.yoesuv.filepicker.menu.main.viewmodels

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.menu.camera.views.CameraActivity
import com.yoesuv.filepicker.menu.gallery.views.GalleryActivity

class MainViewModel: ViewModel() {

    fun clickGallery(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, GalleryActivity::class.java))
    }

    fun clickCamera(view: View) {
        val ctx = view.context
        ctx.startActivity(Intent(ctx, CameraActivity::class.java))
    }

}