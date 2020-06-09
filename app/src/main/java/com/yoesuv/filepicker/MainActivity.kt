package com.yoesuv.filepicker

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codekidlabs.storagechooser.StorageChooser
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.utils.checkAppPermission
import com.yoesuv.filepicker.utils.logDebug

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

    private fun openFileChooser() {
        checkAppPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, {
            chooserBuilder.show()
        }, {

        })
    }
}