package com.yoesuv.filepicker.menu.gallery.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_MEDIA_VISUAL_USER_SELECTED
import com.yoesuv.filepicker.data.PERM_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.data.PERM_READ_MEDIA_IMAGES
import com.yoesuv.filepicker.databinding.ActivityGalleryBinding
import com.yoesuv.filepicker.menu.gallery.viewmodels.GalleryViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.isTiramisu
import com.yoesuv.filepicker.utils.isUpsideDown
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showSnackbarError

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()

    private val permissionGallery =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                openGallery()
            } else {
                val msg1 = R.string.rationale_media_images
                val msg2 = R.string.rationale_read_storage_gallery
                showSnackbarError(if (isTiramisu()) msg1 else msg2)
            }
        }
    private val resultGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uriData = result.data?.data
            logDebug("GalleryActivity # uri : $uriData")
            viewModel.setImageFile(this, uriData)
        }
    }

    private val permissionGallery34 =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            var isGranted = true
            for (value in results.values) {
                if (!value) {
                    isGranted = false
                    break
                }
            }
            for (result in results.entries) {
                logDebug("GalleryActivity #  ${result.key} = ${result.value}")
            }
            if (isGranted) {
                openGallery()
            } else {
                val msg1 = R.string.rationale_media_images
                showSnackbarError(msg1)
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
            if (isUpsideDown()) {
                if (hasPermission(this, PERM_READ_MEDIA_IMAGES)) {
                    openGallery()
                } else {
                    permissionGallery34.launch(arrayOf(PERM_READ_MEDIA_IMAGES, PERM_MEDIA_VISUAL_USER_SELECTED))
                }
            } else {
                val thePermission = if (isTiramisu()) PERM_READ_MEDIA_IMAGES else PERM_READ_EXTERNAL_STORAGE
                if (hasPermission(this, thePermission)) {
                    openGallery()
                } else {
                    permissionGallery.launch(thePermission)
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultGallery.launch(intent)
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