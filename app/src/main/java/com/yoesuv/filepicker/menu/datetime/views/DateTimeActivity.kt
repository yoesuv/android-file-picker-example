package com.yoesuv.filepicker.menu.datetime.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.databinding.ActivityDateTimeBinding
import com.yoesuv.filepicker.utils.logDebug
import java.util.GregorianCalendar

class DateTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_time)
        binding.lifecycleOwner = this

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
        binding.buttonOpenDatePicker.setOnClickListener {
            val dialog = DatePickerDialog(this )
            dialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                logDebug("DateTimeActivity # date $dayOfMonth month $monthOfYear year $year")
                // convert to millis
                val calendar = GregorianCalendar(year, monthOfYear, dayOfMonth)
                val inMilis = calendar.timeInMillis
            }
            dialog.show()
        }
    }

}