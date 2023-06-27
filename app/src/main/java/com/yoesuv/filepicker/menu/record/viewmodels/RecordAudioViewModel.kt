package com.yoesuv.filepicker.menu.record.viewmodels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.data.RecordingState

class RecordAudioViewModel: ViewModel() {

    var recordState = MutableLiveData<RecordingState>()

    init {
        recordState.value = RecordingState.START
    }

    fun startRecording(activity: Activity) {

    }

    fun stopRecording(activity: Activity) {

    }

}