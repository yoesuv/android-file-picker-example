package com.yoesuv.filepicker

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codekidlabs.storagechooser.StorageChooser
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.utils.checkAppPermission
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToast
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var chooserBuilder: StorageChooser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chooserBuilder = StorageChooser.Builder()
            .withActivity(this)
            .withFragmentManager(fragmentManager)
            .disableMultiSelect()
            .withMemoryBar(true)
            .allowCustomPath(true)
            .setType(StorageChooser.FILE_PICKER)
            .build()

        chooserBuilder.setOnSelectListener {
            logDebug("MainActivity # selected $it")
        }

        binding.buttonChooser.setOnClickListener {
            openFileChooser()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun openFileChooser() {
        if (hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showToast("Permission Granted")
        } else {
            showToast(R.string.toast_permission_denied)
        }
    }
}