package com.yoesuv.filepicker.menu.gallery.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel: ViewModel() {

    var imageUri: MutableLiveData<Uri?> = MutableLiveData()

}