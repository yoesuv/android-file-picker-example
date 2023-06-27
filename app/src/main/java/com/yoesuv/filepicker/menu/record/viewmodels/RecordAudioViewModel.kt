package com.yoesuv.filepicker.menu.record.viewmodels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.data.RecordingState
import com.yoesuv.filepicker.utils.logDebug

class RecordAudioViewModel: ViewModel() {

    var recordState = MutableLiveData<RecordingState>()

    init {
        recordState.value = RecordingState.START
    }

    fun startRecording(activity: Activity) {
        logDebug("RecordAudioViewModel # START RECORDING")
        recordState.postValue(RecordingState.RECORDING)
    }

    fun stopRecording(activity: Activity) {
        logDebug("RecordAudioViewModel # STOP RECORDING")
        recordState.postValue(RecordingState.STOP)
    }

}