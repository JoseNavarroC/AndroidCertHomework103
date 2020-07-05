package com.android.cert103

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.cert103.DetailsActivity.Companion.EXTRA_DATA

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val data = intent?.extras?.getString(EXTRA_DATA)
        data?.let {
            context?.let {
                context.cancelNotification()
                Toast.makeText(context, "Recordatorio cancelado", Toast.LENGTH_LONG).show()
            }
        }
    }

}