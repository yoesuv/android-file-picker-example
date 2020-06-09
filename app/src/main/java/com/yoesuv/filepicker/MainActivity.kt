package com.yoesuv.filepicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoesuv.filepicker.databinding.ActivityMainBinding
import com.yoesuv.filepicker.utils.FileHelper
import com.yoesuv.filepicker.utils.checkAppPermission

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQ_CODE = 14;
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonChooser.setOnClickListener {
            checkAppPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, {
                FileHelper.chooseFile(this, REQ_CODE)
            }, {

            })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }
}