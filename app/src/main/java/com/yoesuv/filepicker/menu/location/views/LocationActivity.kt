package com.yoesuv.filepicker.menu.location.views

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_FINE_LOCATION
import com.yoesuv.filepicker.data.RC_FINE_LOCATION
import com.yoesuv.filepicker.databinding.ActivityLocationBinding
import com.yoesuv.filepicker.menu.location.viewmodels.LocationViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.showToastError
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

        viewModel.setup(this)

        setupToolbar()
        setupButton()
        observeData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressedDispatcher.onBackPressed()
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
            if (hasPermission(this, PERM_FINE_LOCATION)) {
                viewModel.getUserLocation(this)
            } else {
                val rationale = getString(R.string.rationale_fine_location)
                EasyPermissions.requestPermissions(this, rationale, RC_FINE_LOCATION, PERM_FINE_LOCATION)
            }
        }
    }

    private fun observeData() {
        viewModel.userLocation.observe(this) {
            binding.tvUserLocation.text = it
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.getUserLocation(this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            showToastError(R.string.toast_permission_access_coarse_location_denied)
        }
    }

}