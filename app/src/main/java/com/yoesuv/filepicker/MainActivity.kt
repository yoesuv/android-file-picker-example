package com.yoesuv.filepicker

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoesuv.filepicker.data.RC_PICK_FILE
import com.yoesuv.filepicker.data.RC_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToast
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            openFilePicker()
        } else {
            val rationale = getString(R.string.rationale_read_storage)
            EasyPermissions.requestPermissions(this, rationale, RC_READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startActivityForResult(intent, RC_PICK_FILE)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        logDebug("MainActivity # onPermissionsGranted -> request code : $requestCode/permissions $perms")
        openFilePicker()
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