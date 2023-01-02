package com.yoesuv.filepicker.menu.file.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.data.RC_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityFileBinding
import com.yoesuv.filepicker.menu.file.viewmodels.FileViewModel
import com.yoesuv.filepicker.utils.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class FileActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityFileBinding
    private val viewModel: FileViewModel by viewModels()

    private val startForResultFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uriData = result.data?.data
            uriData?.let {
                viewModel.setSelectedFile(this, it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file)
        binding.lifecycleOwner = this
        binding.file = viewModel

        setupToolbar()
        setupButton()
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
        supportActionBar?.setTitle(R.string.button_file)
    }

    private fun setupButton() {
        binding.buttonChooseFile.setOnClickListener {
            if (isTiramisu()) {
                openFilePicker()
            } else {
                if (hasPermission(this, PERM_READ_EXTERNAL_STORAGE)) {
                    openFilePicker()
                } else {
                    val rationale = getString(R.string.rationale_read_storage)
                    EasyPermissions.requestPermissions(
                        this,
                        rationale,
                        RC_READ_EXTERNAL_STORAGE,
                        PERM_READ_EXTERNAL_STORAGE
                    )
                }
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        val chooser = Intent.createChooser(intent, getString(R.string.button_choose_file))
        startForResultFile.launch(chooser)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        openFilePicker()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            showToastError(R.string.toast_permission_read_storage_denied)
        }
    }

}