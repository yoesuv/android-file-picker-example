package com.yoesuv.filepicker.menu.gallery.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityGalleryBinding
import com.yoesuv.filepicker.menu.gallery.viewmodels.GalleryViewModel
import com.yoesuv.filepicker.utils.isTiramisu
import com.yoesuv.filepicker.utils.showSnackbarError

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()

    private val permissionGallery = registerForActivityResult(RequestPermission()) { result ->
        if (result) {
            openGallery()
        } else {
            val msg = R.string.rationale_media_images
            showSnackbarError(msg)
        }
    }

    private val resultGallery = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uriData = result.data?.data
            viewModel.setImageFile(this, uriData)
        }
    }

    private val resultGallery33 = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.setImageFile(this, uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        binding.lifecycleOwner = this
        binding.gallery = viewModel

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
        supportActionBar?.setTitle(R.string.button_gallery)
    }

    private fun setupButton() {
        binding.buttonOpenGallery.setOnClickListener {
            if (isTiramisu()) {
                openGallery33()
            } else {
                permissionGallery.launch(PERM_READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultGallery.launch(intent)
    }

    private fun openGallery33() {
        resultGallery33.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    private fun observeData() {
        viewModel.imageFile.observe(this) {
            Glide.with(this).load(it)
                .centerCrop()
                .dontAnimate()
                .into(binding.ivGallery)
        }
    }

}