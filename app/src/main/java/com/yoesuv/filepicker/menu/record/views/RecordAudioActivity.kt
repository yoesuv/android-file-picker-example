package com.yoesuv.filepicker.menu.record.views

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_RECORD_AUDIO
import com.yoesuv.filepicker.data.PERM_WRITE_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityRecordAudioBinding
import com.yoesuv.filepicker.menu.record.viewmodels.RecordAudioViewModel
import com.yoesuv.filepicker.utils.isPieOrLower
import com.yoesuv.filepicker.utils.showToastError

class RecordAudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordAudioBinding
    private val viewModel: RecordAudioViewModel by viewModels()

    private val requestRecord = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            viewModel.startRecording(this)
        } else {
            val msg = R.string.toast_permission_record_audio_denied
            showToastError(msg)
        }
    }
    private val requestRecordAndWrite =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            if (results.all { it.value }) {
                viewModel.startRecording(this)
            } else {
                val msg = R.string.toast_permission_record_audio_and_write_denied
                showToastError(msg)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record_audio)
        binding.lifecycleOwner = this
        binding.record = viewModel

        setupToolbar()
        observeData()
        setupButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecording(this)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_record_audio)
    }

    private fun observeData() {
        viewModel.recordState.observe(this) {
            val str = getString(R.string.is_recording, it.name)
            binding.tvRecordingState.text = str
        }
    }

    private fun setupButton() {
        binding.buttonStartRecord.setOnClickListener {
            if (isPieOrLower()) {
                val permissions = arrayOf(PERM_RECORD_AUDIO, PERM_WRITE_EXTERNAL_STORAGE)
                requestRecordAndWrite.launch(permissions)
            } else {
                requestRecord.launch(PERM_RECORD_AUDIO)
            }
        }
        binding.buttonStopRecord.setOnClickListener {
            viewModel.stopRecording(this)
        }
    }

}