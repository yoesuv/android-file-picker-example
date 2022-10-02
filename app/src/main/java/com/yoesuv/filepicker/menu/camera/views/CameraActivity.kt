package com.yoesuv.filepicker.menu.camera.views

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RC_CAMERA
import com.yoesuv.filepicker.databinding.ActivityCameraBinding
import com.yoesuv.filepicker.menu.camera.viewmodels.CameraViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.showToastError
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class CameraActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityCameraBinding
    private val viewModel: CameraViewModel by viewModels()
    private var photoUri: Uri? = null

    private val startForCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            viewModel.setPhotoUri(this, photoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        binding.lifecycleOwner = this
        binding.camera = viewModel

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
        supportActionBar?.setTitle(R.string.button_camera)
    }

    private fun setupButton() {
        binding.buttonOpenCamera.setOnClickListener {
            if (hasPermission(this, Manifest.permission.CAMERA)) {
                openCamera()
            } else {
                val rationale = getString(R.string.rationale_open_camera)
                EasyPermissions.requestPermissions(this, rationale, RC_CAMERA, Manifest.permission.CAMERA)
            }
        }
    }

    private fun observeData() {
        viewModel.imageFile.observe(this) { file ->
            if (file != null) {
                binding.tvCameraPath.text = file.absolutePath
                Glide.with(this).load(file)
                    .centerCrop()
                    .dontAnimate()
                    .into(binding.ivCamera)
            }
        }
    }

    private fun openCamera() {
        photoUri = viewModel.uriCamera(this)
        startForCamera.launch(photoUri)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        openCamera()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            showToastError(R.string.toast_permission_camera_denied)
        }
    }

}