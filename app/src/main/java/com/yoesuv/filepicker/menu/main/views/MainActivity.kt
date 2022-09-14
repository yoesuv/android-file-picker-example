package com.yoesuv.filepicker.menu.main.views

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RC_COARSE_LOCATION
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.menu.main.viewmodels.MainViewModel
import com.yoesuv.filepicker.utils.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.main = viewModel
    }

    private fun checkPermissionLocation() {
        if (hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

        } else {
            val rationale = getString(R.string.rationale_fine_location)
            EasyPermissions.requestPermissions(this, rationale, RC_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }


}