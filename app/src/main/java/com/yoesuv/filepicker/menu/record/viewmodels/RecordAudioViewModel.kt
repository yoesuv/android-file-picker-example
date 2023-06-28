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
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.time.Duration.Companion.milliseconds

class RecordAudioViewModel : ViewModel() {

    var recordState = MutableLiveData<RecordingState>()
    var recordDuration = MutableLiveData("00:00:000")
    var recordRunning = MutableLiveData("00:00")
    var showPlayer = MutableLiveData(false)

    private var recorder: MediaRecorder? = null
    private var fileName: String = ""
    private var player: MediaPlayer? = null

    private var timer: Timer? = null
    private var secondRunning = 0

    init {
        recordState.value = RecordingState.START
    }

    fun startRecording(activity: Activity) {
        showPlayer.postValue(false)
        val state = recordState.value
        if (state == RecordingState.RECORDING) {
            recordState.postValue(RecordingState.PAUSE)
            recorder?.pause()
            timer?.cancel()
        } else if (state == RecordingState.PAUSE) {
            recordState.postValue(RecordingState.RESUME)
            recorder?.resume()
            runTimer()
        } else if (state == RecordingState.RESUME) {
            recordState.postValue(RecordingState.PAUSE)
            recorder?.pause()
            timer?.cancel()
        } else {
            logDebug("RecordAudioViewModel # START RECORDING")
            secondRunning = 0
            runTimer()
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
                    timer?.cancel()
                    e.printStackTrace()
                    activity.showToastError(R.string.toast_record_failed)
                }
                start()
            }
        }
    }

    fun stopRecording() {
        recordState.postValue(RecordingState.STOP)
        recordRunning.postValue("00:00")
        timer?.cancel()
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        calculateDuration()
        showPlayer.postValue(true)
    }

    fun playRecord(activity: Activity) {
        recordState.postValue(RecordingState.STOP)
        timer?.cancel()
        if (fileName.isNotEmpty()) {
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
    }

    fun stopPlayRecord() {
        player?.apply {
            stop()
            release()
        }
        player = null
    }

    private fun calculateDuration() {
        if (fileName.isNotEmpty()) {
            player = MediaPlayer()
            player?.setDataSource(fileName)
            player?.prepare()
            val duration = player?.duration ?: 0
            val theDuration = duration.milliseconds
            theDuration.toComponents { minute, second, nanosec ->
                val strSec = "$second".padStart(2, '0')
                val strMin = "$minute".padStart(2, '0')
                if (nanosec.toString().length > 3) {
                    val strNanosec = nanosec.toString().substring(0, 3)
                    recordDuration.postValue("$strMin:$strSec:$strNanosec")
                } else {
                    recordDuration.postValue("$strMin:$strSec:$nanosec")
                }
            }
        }
    }

    private fun runTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask {
            secondRunning++
            val sec = secondRunning % 60
            val min = secondRunning / 60
            val strSec = "$sec".padStart(2, '0')
            val strMin = "$min".padStart(2, '0')
            recordRunning.postValue("$strMin:$strSec")
        }, 1000L, 1000L)
    }

}