package com.yoesuv.filepicker.menu.download.views

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RC_WRITE_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityDownloadBinding
import com.yoesuv.filepicker.menu.download.viewmodels.DownloadViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToast
import pub.devrel.easypermissions.EasyPermissions

class DownloadActivity: AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityDownloadBinding
    private val viewModel: DownloadViewModel by viewModels()

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
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
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
        if (hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            viewModel.downloadFile(this)
        } else {
            val rationale = getString(R.string.rationale_write_storage)
            EasyPermissions.requestPermissions(
                this,
                rationale,
                RC_WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_WRITE_EXTERNAL_STORAGE) {
            viewModel.downloadFile(this)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

        } else {
            if (requestCode == RC_WRITE_EXTERNAL_STORAGE) {
                showToast(R.string.toast_permission_write_storage_denied)
            }
        }
    }

}