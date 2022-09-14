package com.yoesuv.filepicker.menu.file.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FileViewModel: ViewModel() {

    var fileUri: MutableLiveData<Uri?> = MutableLiveData()
}