package com.yoesuv.filepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoesuv.filepicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonChooser.setOnClickListener {

        }
    }
}