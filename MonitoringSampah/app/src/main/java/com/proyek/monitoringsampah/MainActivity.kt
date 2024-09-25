package com.proyek.monitoringsampah

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.proyek.monitoringsampah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var b: ActivityMainBinding // Ganti dengan nama binding yang sesuai
    private lateinit var notificationManager: NotificationManager

    companion object {
        private const val CHANNEL_ID = "Channel_ID"
        private const val CHANNEL_NAME = "Channel_Name"
        private const val NOTIFICATION_ID = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        FirebaseMessaging.getInstance().subscribeToTopic("status_2")

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("sensor_data")
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status2 = snapshot.child("status_2").value.toString()

                b.statusTextView.text = "Status 2: $status2"

                // Jika status_2 adalah "Full", kirimkan notifikasi ke aplikasi
                if (status2 == "Full") {
                    sendNotification("Sampah Penuh", "Sampah sudah penuh, waktunya buang sampahh!!")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if needed
            }
        })
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}