package com.yoesuv.filepicker.menu.datetime.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.databinding.ActivityDateTimeBinding
import com.yoesuv.filepicker.menu.datetime.viewmodels.DateTimeViewModel
import com.yoesuv.filepicker.utils.logDebug
import java.util.Calendar

class DateTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateTimeBinding
    private val viewModel: DateTimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_time)
        binding.lifecycleOwner = this
        binding.dateTime = viewModel

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
        supportActionBar?.setTitle(R.string.button_date_time)
    }

    private fun setupButton() {
        binding.buttonGetCurrentDateTime.setOnClickListener {
            viewModel.getCurrentDateTime()
        }
        binding.buttonOpenDatePicker.setOnClickListener {
            val dialog = DatePickerDialog(this)
            dialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                logDebug("DateTimeActivity # date $dayOfMonth month $monthOfYear year $year")
                viewModel.setDate(year, monthOfYear, dayOfMonth)
            }
            dialog.show()
        }
        binding.buttonOpenTimePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val min = calendar.get(Calendar.MINUTE)
            val dialog = TimePickerDialog(this, { _, hourOfDay, minute ->
                viewModel.setTime(hourOfDay, minute)
            }, hour, min, true)
            dialog.show()
        }
    }

}