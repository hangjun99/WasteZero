package kr.ac.konkuk.wastezero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kr.ac.konkuk.wastezero.ui.main.MainFragment
import timber.log.Timber

class IngredientAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (intent.getStringExtra(MainFragment.NOTIFICATION_REQUEST_KEY_MAIN) == MainFragment.NOTIFICATION_REQUEST_CODE_MAIN) {
                Toast.makeText(context, "Alarm Start", Toast.LENGTH_SHORT).show()
                val count = intent.getIntExtra("count", 0)
                Timber.d("myLog", "$count")
            }
        }
    }
}
