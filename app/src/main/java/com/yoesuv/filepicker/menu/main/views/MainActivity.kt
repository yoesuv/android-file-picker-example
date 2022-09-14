package com.yoesuv.filepicker.menu.main.views

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RC_FINE_LOCATION
import com.yoesuv.filepicker.data.RC_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.menu.main.viewmodels.MainViewModel
import com.yoesuv.filepicker.utils.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.main = viewModel

        binding.buttonLocation.setOnClickListener {
            checkPermissionLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun checkPermissionLocation() {
        if (hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            getUserLocation()
        } else {
            val rationale = getString(R.string.rationale_fine_location)
            EasyPermissions.requestPermissions(this, rationale, RC_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getUserLocation() {
        logDebug("MainActivity # get user location")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getUserLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        logDebug("MainActivity # request code $requestCode/permission $perms")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            if (requestCode == RC_READ_EXTERNAL_STORAGE) {
                showToast(R.string.toast_permission_read_storage_denied)
            } else if (requestCode == RC_FINE_LOCATION) {
                showToast(R.string.toast_permission_access_fine_location_denied)
            }
        }
    }

}