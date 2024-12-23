package kr.ac.konkuk.wastezero.util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.ui.MainActivity

object Notification {
    private const val CHANNEL_ID = "IngredientChannel"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "보관기간이 임박한 식재료 알림"
            val descriptionText = "보관기간이 1~2일 정도 남은 식재료들을 빠르게 소진하기 위해서 확인 알림이 발행된다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context) {
        val notificationId = 1

        // 알림을 클릭했을 때 실행될 Intent
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 빌더
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo) // 알림 아이콘
            /*.setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_logo
                )
            ) // 알림 아이콘*/
//            .setContentTitle("Example Notification")
//            .setContentText("This is an example notification.")
            .setContentText("쪽파, 양파 외 3가지 재료의 유통기한이 얼마 안남았어요 레시피를 참고하여 요리를 만들어 보세요")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // 클릭 시 실행할 작업
            .setAutoCancel(true) // 알림 클릭 시 삭제
            .build()

        // 알림 표시
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

}