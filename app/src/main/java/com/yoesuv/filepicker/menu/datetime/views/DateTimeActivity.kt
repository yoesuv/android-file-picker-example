package com.yoesuv.filepicker.menu.datetime.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.databinding.ActivityDateTimeBinding

class DateTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_time)
        binding.lifecycleOwner = this

        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.button_date_time)
    }

}