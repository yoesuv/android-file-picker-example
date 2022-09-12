package com.yoesuv.filepicker.menu.camera.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel: ViewModel() {

    var imageUri: MutableLiveData<Uri?> = MutableLiveData()

}