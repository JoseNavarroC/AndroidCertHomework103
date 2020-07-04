package com.android.cert103

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerComplexNotification(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return if (applicationContext.createNotification()) Result.success()
        else Result.failure()
    }

}