package com.yoesuv.filepicker.menu.record.views

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.databinding.ActivityRecordAudioBinding
import com.yoesuv.filepicker.menu.record.viewmodels.RecordAudioViewModel

class RecordAudioActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRecordAudioBinding
    private val viewModel: RecordAudioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record_audio)
        binding.lifecycleOwner = this
        binding.record = viewModel

        setupToolbar()
        observeData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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

}