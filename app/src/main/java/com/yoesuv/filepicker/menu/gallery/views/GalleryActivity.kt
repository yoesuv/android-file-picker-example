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
import com.yoesuv.filepicker.data.PERM_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.data.PERM_READ_MEDIA_IMAGES
import com.yoesuv.filepicker.data.RC_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityGalleryBinding
import com.yoesuv.filepicker.menu.gallery.viewmodels.GalleryViewModel
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.isTiramisu
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToastError
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class GalleryActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()

    private val startForResultGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uriData = result.data?.data
                logDebug("GalleryActivity # uri : $uriData")
                viewModel.setImageFile(this, uriData)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_gallery)
    }

    private fun setupButton() {
        val thePermission = if (isTiramisu()) PERM_READ_MEDIA_IMAGES else PERM_READ_EXTERNAL_STORAGE
        binding.buttonOpenGallery.setOnClickListener {
            if (hasPermission(this, thePermission)) {
                openGallery()
            } else {
                val msg1 = getString(R.string.rationale_media_images)
                val msg2 = getString(R.string.rationale_read_storage_gallery)
                EasyPermissions.requestPermissions(
                    this,
                    if (isTiramisu()) msg1 else msg2,
                    RC_READ_EXTERNAL_STORAGE,
                    thePermission
                )
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startForResultGallery.launch(intent)
    }

    private fun observeData() {
        viewModel.imageFile.observe(this) {
            Glide.with(this).load(it)
                .centerCrop()
                .dontAnimate()
                .into(binding.ivGallery)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        openGallery()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            val msg1 = R.string.toast_permission_images_denied
            val msg2 = R.string.toast_permission_read_storage_denied
            val msgDenied = if (isTiramisu()) msg1 else msg2
            showToastError(msgDenied)
        }
    }

}