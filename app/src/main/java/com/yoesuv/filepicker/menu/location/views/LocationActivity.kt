package com.yoesuv.filepicker.menu.location.views

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_FINE_LOCATION
import com.yoesuv.filepicker.databinding.ActivityLocationBinding
import com.yoesuv.filepicker.menu.location.viewmodels.LocationViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.showToastError

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    private val viewModel: LocationViewModel by viewModels()

    private val permissionLocation = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            viewModel.getUserLocation(this)
        } else {
            val msg = R.string.rationale_fine_location
            showToastError(msg)
        }
    }

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

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_location)
    }

    private fun setupButton() {
        binding.buttonUserLocation.setOnClickListener {
            if (hasPermission(this, PERM_FINE_LOCATION)) {
                viewModel.getUserLocation(this)
            } else {
                permissionLocation.launch(PERM_FINE_LOCATION)
            }
        }
    }

    private fun observeData() {
        viewModel.userLocation.observe(this) {
            binding.tvUserLocation.text = it
        }
    }

}