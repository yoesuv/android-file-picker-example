package com.yoesuv.filepicker.menu.datetime.viewmodels

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

class DateTimeViewModel : ViewModel() {

    var currentDateTimeValue = MutableLiveData("-")

    fun getCurrentDateTime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val time = LocalDateTime.now()
            val day = time.dayOfMonth.toString().padStart(2, '0')
            val month = time.monthValue.toString().padStart(2, '0')
            val year = time.year
            val hour = time.hour.toString().padStart(2, '0')
            val minute = time.minute.toString().padStart(2, '0')
            val second = time.second.toString().padStart(2, '0')
            currentDateTimeValue.value = "$day/$month/$year $hour:$minute:$second"
        }
    }

}