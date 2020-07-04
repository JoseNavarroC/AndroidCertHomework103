package com.android.cert103

import android.os.Bundle
import android.text.format.DateFormat
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

// Crear un Worker que se ejecute cada X tiempo y lanze una notificación que ocupe un action
// sebastian.tellez.o@gmail.com

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMainShow?.setOnClickListener {
//            val request = buildOneTimeWorkerRequest()
//            WorkManager.getInstance(this).enqueue(request)
            showNotif()
        }
    }

    private fun buildOneTimeWorkerRequest(): OneTimeWorkRequest {

        val service =
            if (System.currentTimeMillis() % 2 == 0L) getString(R.string.service_electricity)
            else getString(R.string.service_internet)

        val calendar = Calendar.getInstance()
            .apply { set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR + 5)) }.time
        val dateLong = DateFormat.getLongDateFormat(this).format(calendar)
        val dateShort = DateFormat.getDateFormat(this).format(calendar)

        val content = getString(R.string.format_content, service, dateShort)
        val bigContent = getString(R.string.format_content_details, service, dateLong)

        val data = workDataOf(
            WorkerHomeworkNotification.KEY_CONTENT to content,
            WorkerHomeworkNotification.KEY_CONTENT_DETAILS to bigContent
        )

        return OneTimeWorkRequestBuilder<WorkerHomeworkNotification>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setInputData(data)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
    }

    private fun buildPeriodicWorkerRequest(): PeriodicWorkRequest {
        val service =
            if (System.currentTimeMillis() % 2 == 0L) getString(R.string.service_electricity)
            else getString(R.string.service_internet)

        val calendar = Calendar.getInstance()
            .apply { set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR + 5)) }.time
        val date = DateFormat.getLongDateFormat(this).format(calendar)

        val content = getString(R.string.format_content, service, date)
        val bigContent = getString(R.string.format_content_details, service, date)

        val data = workDataOf(
            WorkerHomeworkNotification.KEY_CONTENT to content,
            WorkerHomeworkNotification.KEY_CONTENT_DETAILS to bigContent
        )

        return PeriodicWorkRequestBuilder<WorkerHomeworkNotification>(15, TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(
                Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            ).build()
    }

    private fun showNotif() {
        val service =
            if (System.currentTimeMillis() % 2 == 0L) getString(R.string.service_electricity)
            else getString(R.string.service_internet)

        val calendar = Calendar.getInstance()
            .apply { set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR + 5)) }.time
        val dateLong = DateFormat.getLongDateFormat(this).format(calendar)
        val dateShort = DateFormat.getDateFormat(this).format(calendar)

        val content = getString(R.string.format_content, service, dateShort)
        val bigContent = getString(R.string.format_content_details, service, dateLong)

        createHomeworkNotification(content, bigContent)
    }

}