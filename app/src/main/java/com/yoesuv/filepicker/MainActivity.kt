package com.yoesuv.filepicker

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.format.Formatter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.yoesuv.filepicker.data.RC_FINE_LOCATION
import com.yoesuv.filepicker.data.RC_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.utils.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoUri: Uri

    //private lateinit var fused: FusedLoc

    private val startForResultFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val uriData = result.data?.data
                uriData?.let { uri ->
                    val fileName = MyFileUtils.getFileName(this, uri)
                    val cacheDir = cacheDir.path + File.separator
                    val inputStream = contentResolver.openInputStream(uri)
                    val tempFile = File(cacheDir + fileName)
                    MyFileUtils.copyToTempFile(inputStream, tempFile)
                    val fileSize = Formatter.formatFileSize(this, tempFile.length())
                    logDebug("MainActivity # file name:${tempFile.name}/size:$fileSize")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val startForCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            logDebug("MainActivity # take photo success: $photoUri")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonChooser.setOnClickListener {
            checkPermissionReadStorage()
        }
        binding.buttonCamera.setOnClickListener {
            openCamera()
        }
        binding.buttonLocation.setOnClickListener {
            checkPermissionLocation()
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

    private fun checkPermissionLocation() {
        if (hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

        } else {
            val rationale = getString(R.string.rationale_fine_location)
            EasyPermissions.requestPermissions(this, rationale, RC_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startForResultFile.launch(intent)
    }

    private fun openCamera() {
        val packageName = applicationContext.packageName
        val photoFile = File.createTempFile("IMG_",".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        photoUri = FileProvider.getUriForFile(this, "$packageName.provider", photoFile)
        startForCamera.launch(photoUri)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_READ_EXTERNAL_STORAGE) {
            openFilePicker()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            if (requestCode == RC_READ_EXTERNAL_STORAGE) {
                showToast(R.string.toast_permission_read_storage_denied)
            }
        }
    }

}