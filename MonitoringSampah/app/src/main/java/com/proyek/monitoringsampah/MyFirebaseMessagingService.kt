import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Memproses pesan yang diterima
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title
            val message = remoteMessage.notification?.body

            if (title != null && message != null) {
                // Membuat dan menampilkan notifikasi
                // Gunakan NotificationManager untuk menampilkan notifikasi di sini
            }
        }
    }
}
