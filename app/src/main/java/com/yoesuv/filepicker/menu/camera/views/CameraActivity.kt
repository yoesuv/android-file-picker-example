package com.yoesuv.filepicker.menu.camera.views

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_CAMERA
import com.yoesuv.filepicker.databinding.ActivityCameraBinding
import com.yoesuv.filepicker.menu.camera.viewmodels.CameraViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.showSnackbarError

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private val viewModel: CameraViewModel by viewModels()
    private var photoUri: Uri? = null

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            openCamera()
        } else {
            val msg = R.string.rationale_open_camera
            showSnackbarError(msg)
        }
    }
    private val resultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
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

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_camera)
    }

    private fun setupButton() {
        binding.buttonOpenCamera.setOnClickListener {
            if (hasPermission(this, PERM_CAMERA)) {
                openCamera()
            } else {
                permissionCamera.launch(PERM_CAMERA)
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
        resultCamera.launch(photoUri)
    }

}