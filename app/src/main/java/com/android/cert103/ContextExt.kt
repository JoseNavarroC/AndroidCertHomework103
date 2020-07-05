package com.android.cert103

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

const val NOTIFICATION_ID = 0
const val NOTIFICATION_CHANNEL = "Aviso de pagos"

fun Context.createHomeworkNotification(
    content: String,
    contentBig: String
): Boolean {

    val activityIntent = Intent(this, DetailsActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(DetailsActivity.EXTRA_DATA, contentBig)
    }
    val activityPending = PendingIntent.getActivity(
        applicationContext,
        0,
        activityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val broadcastIntent = Intent(this, ReminderReceiver::class.java).apply {
        action = "PAY_REMINDER_CANCELED"
        putExtra(DetailsActivity.EXTRA_DATA, contentBig)
    }
    val reminderPendingIntent = PendingIntent.getBroadcast(applicationContext, 0, broadcastIntent, 0)

    val builder =
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Recordatorio de pago servicios")
            setContentText(content)
            setStyle(
                NotificationCompat.BigTextStyle().bigText(contentBig)
            )
            setAutoCancel(true)
            setVibrate(longArrayOf(500, 0, 500, 0))
            setContentIntent(activityPending)
            addAction(R.drawable.cash_usd_outline, "Cancelar recordatorio", reminderPendingIntent)
            priority = NotificationCompat.PRIORITY_HIGH
        }

    val manager = NotificationManagerCompat.from(this).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(createHomeworkNotificationChannel())
        }
    }

    manager.notify(NOTIFICATION_ID, builder.build())
    return true
}

fun Context.createNotification(): Boolean {
    val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
    val builder =
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Titulo de la notificación")
            setContentText("Notificación del curso de certificación de Google")
            setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText("Notificatión del curso de certificación de Google para tener conocimiento sobre el manejo de notificaciones")
            )
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_HIGH
        }

    val manager = NotificationManagerCompat.from(this).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(createNotificationChannel())
        }
    }

    manager.notify(NOTIFICATION_ID, builder.build())
    return true
}

fun Context.cancelNotification() {
    val manager = NotificationManagerCompat.from(this)
    manager.cancel(NOTIFICATION_ID)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel() =
    NotificationChannel(
        NOTIFICATION_CHANNEL, "Cert App Notification Channel",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        enableLights(true)
        enableVibration(true)
        lightColor = Color.BLUE
        description = "Notificaciones para el curso de Android"
    }

@RequiresApi(Build.VERSION_CODES.O)
private fun createHomeworkNotificationChannel() =
    NotificationChannel(
        NOTIFICATION_CHANNEL, "Aviso de pagos",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        enableLights(true)
        enableVibration(true)
        lightColor = Color.GREEN
        description = "Notificaciones implementadas para notificar sobre pagos próximos"
    }