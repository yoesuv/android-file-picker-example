package com.yoesuv.filepicker

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codekidlabs.storagechooser.StorageChooser
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToast
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val RC_READ_STORAGE: Int = 23

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
            checkPermissionReadStorage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun checkPermissionReadStorage() {
        if (hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            val rationale = getString(R.string.rationale_read_storage)
            EasyPermissions.requestPermissions(this, rationale, RC_READ_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        logDebug("MainActivity # onPermissionsGranted -> request code : $requestCode/permissions $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        logDebug("MainActivity # onPermissionDenied -> request code : $requestCode/permission $perms")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            showToast(R.string.toast_permission_read_storage_denied)
        }
    }


}