package com.yoesuv.filepicker.menu.location.views

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RC_COARSE_LOCATION
import com.yoesuv.filepicker.databinding.ActivityLocationBinding
import com.yoesuv.filepicker.menu.location.viewmodels.LocationViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.showToast
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class LocationActivity: AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityLocationBinding
    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location)
        binding.lifecycleOwner = this
        binding.location = viewModel

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
        supportActionBar?.setTitle(R.string.button_location)
    }

    private fun setupButton() {
        binding.buttonUserLocation.setOnClickListener {
            if (hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                viewModel.getUserLocation()
            } else {
                val rationale = getString(R.string.rationale_fine_location)
                EasyPermissions.requestPermissions(this, rationale, RC_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.getUserLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            showToast(R.string.toast_permission_access_coarse_location_denied)
        }
    }

}