package com.yoesuv.filepicker.menu.record.viewmodels

import android.app.Activity
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RecordingState
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToastError
import java.io.IOException

class RecordAudioViewModel : ViewModel() {

    var recordState = MutableLiveData<RecordingState>()

    private var recorder: MediaRecorder? = null
    private var fileName: String = ""
    private var player: MediaPlayer? = null

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
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                activity.showToastError(R.string.toast_record_failed)
            }
            start()
        }
    }

    fun stopRecording() {
        recordState.postValue(RecordingState.STOP)
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    fun playRecord(activity: Activity) {
        recordState.postValue(RecordingState.STOP)
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
                activity.showToastError(R.string.toast_play_record_failed)
            }
        }
    }

    fun stopPlayRecord() {
        player?.apply {
            stop()
            release()
        }
        player = null
    }

}