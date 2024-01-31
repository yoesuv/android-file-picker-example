package com.yoesuv.filepicker.menu.notification.views

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.PERM_POST_NOTIFICATION
import com.yoesuv.filepicker.databinding.ActivityNotificationBinding
import com.yoesuv.filepicker.utils.isTiramisu
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showSnackbarError

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    private val requestPost = registerForActivityResult(RequestPermission()) { result ->
        if (result) {
            logDebug("NotificationActivity # PUSH GRANTED")
        } else {
            val msg = R.string.toast_push_permission_denied
            showSnackbarError(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
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
        supportActionBar?.setTitle(R.string.button_notification)
    }

    private fun setupButton() {
        binding.buttonNormalNotification.setOnClickListener {
            if (isTiramisu()) {
                requestPost.launch(PERM_POST_NOTIFICATION)
            }
        }
    }

}