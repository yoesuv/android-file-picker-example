package com.yoesuv.filepicker.menu.download.views

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_WRITE_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityDownloadBinding
import com.yoesuv.filepicker.menu.download.viewmodels.DownloadViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToastError

class DownloadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownloadBinding
    private val viewModel: DownloadViewModel by viewModels()

    private val permissionWrite = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            viewModel.downloadFile(this)
        } else {
            val msg = R.string.rationale_write_storage
            showToastError(msg)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
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
            this.onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_download)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupButton() {
        binding.buttonDownloadFile.setOnClickListener {
            val sdkInt = Build.VERSION.SDK_INT
            logDebug("DownloadActivity # device SDK : $sdkInt")
            if (sdkInt <= Build.VERSION_CODES.P) {
                setupDownloadSDK28()
            } else {
                viewModel.downloadFileSdk29(this)
            }
        }
    }

    private fun setupDownloadSDK28() {
        if (hasPermission(this, PERM_WRITE_EXTERNAL_STORAGE)) {
            viewModel.downloadFile(this)
        } else {
            permissionWrite.launch(PERM_WRITE_EXTERNAL_STORAGE)
        }
    }

}