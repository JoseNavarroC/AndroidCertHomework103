package com.android.cert103

import android.content.Context
import android.text.format.DateFormat
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class WorkerHomeworkNotification(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val content = inputData.getString(KEY_CONTENT) ?: ""
        val contentDetails = inputData.getString(KEY_CONTENT_DETAILS) ?: ""

        return if (applicationContext.createHomeworkNotification(
                content,
                contentDetails
            )
        ) Result.success()
        else Result.failure()
    }

    companion object {
        const val KEY_CONTENT = "key.content"
        const val KEY_CONTENT_DETAILS = "key.content-details"
    }

}