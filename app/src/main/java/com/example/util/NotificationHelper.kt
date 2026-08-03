package com.example.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity

object NotificationHelper {

    fun sendDesignNotification(context: Context, orderNumber: String, driveLink: String) {
        val channelId = "jitu_studio_orders_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Jitu Gallery Studio Order Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications when graphic design orders are completed"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = if (driveLink.isNotBlank()) {
            Intent(Intent.ACTION_VIEW, Uri.parse(driveLink))
        } else {
            Intent(context, MainActivity::class.java)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            orderNumber.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle("Order $orderNumber - Design Completed!")
            .setContentText("Successfully designed, please click the link to download")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Successfully designed, please click the link to download\n\nGoogle Drive Link: $driveLink")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(orderNumber.hashCode(), notification)
    }
}
