package com.yoesuv.filepicker.menu.download.views

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.databinding.ActivityDownloadBinding
import com.yoesuv.filepicker.menu.download.viewmodels.DownloadViewModel
import com.yoesuv.filepicker.utils.logDebug

class DownloadActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDownloadBinding
    private val viewModel: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download)
        binding.lifecycleOwner = this
        binding.download = viewModel

        setupToolbar()
        setupButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_download)
    }

    private fun setupButton() {
        binding.buttonDownloadFile.setOnClickListener {
            val sdkInt = Build.VERSION.SDK_INT
            logDebug("DownloadActivity # device SDK : $sdkInt")
            if (sdkInt < Build.VERSION_CODES.R) {
                // request write external storage
                logDebug("DownloadActivity # request permission write")
            } else {
                // other
                logDebug("DownloadActivity # other")
            }
        }
    }

}