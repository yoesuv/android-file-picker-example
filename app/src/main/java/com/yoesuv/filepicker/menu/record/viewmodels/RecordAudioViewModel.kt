package com.yoesuv.filepicker.menu.record.viewmodels

import android.app.Activity
import android.media.MediaRecorder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.data.RecordingState
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.logError
import java.io.IOException

class RecordAudioViewModel: ViewModel() {

    var recordState = MutableLiveData<RecordingState>()

    private var recorder: MediaRecorder? = null
    private var fileName: String = ""

    init {
        recordState.value = RecordingState.START
    }

    fun startRecording(activity: Activity) {
        logDebug("RecordAudioViewModel # START RECORDING")
        val cachePath = "${activity.externalCacheDir?.absolutePath}"
        fileName = "$cachePath/record_audio.aac"
        logDebug("RecordAudioViewModel # fileName: $fileName")
        recordState.postValue(RecordingState.RECORDING)
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                logError("RecordAudioViewModel # prepare() failed")
            }
            start()
        }
    }

    fun stopRecording(activity: Activity) {
        logDebug("RecordAudioViewModel # STOP RECORDING")
        recordState.postValue(RecordingState.STOP)
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

}