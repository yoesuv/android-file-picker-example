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
import com.yoesuv.filepicker.databinding.ActivityFileBinding
import com.yoesuv.filepicker.menu.file.viewmodels.FileViewModel
import com.yoesuv.filepicker.utils.*

class FileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileBinding
    private val viewModel: FileViewModel by viewModels()

    private val permissionRead = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openFilePicker()
        } else {
            showSnackbarError(R.string.toast_permission_read_storage_denied)
        }
    }

    private val startForResultFile =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                    permissionRead.launch(PERM_READ_EXTERNAL_STORAGE)
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

}