package kr.ac.konkuk.wastezero.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ActivitySplashScreenBinding
import kr.ac.konkuk.wastezero.ui.login.LoginActivity
import kr.ac.konkuk.wastezero.util.base.BaseActivity

//@AndroidEntryPoint
class SplashScreen(

) : BaseActivity<ActivitySplashScreenBinding>(R.layout.activity_splash_screen) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkAuthState()
    }

    private fun setSplashScreen() {
        val splashScreen = installSplashScreen()
        // Wait until the app is ready before switching to the content
        splashScreen.setKeepOnScreenCondition { true }
    }

    private fun checkAuthState() {
        val auth: FirebaseAuth = Firebase.auth

        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()

        return
    }

}