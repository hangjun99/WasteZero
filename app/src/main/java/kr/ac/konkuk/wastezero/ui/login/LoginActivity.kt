package kr.ac.konkuk.wastezero.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ActivityLoginBinding
import kr.ac.konkuk.wastezero.ui.MainActivity
import kr.ac.konkuk.wastezero.util.base.BaseActivity
import timber.log.Timber

//@AndroidEntryPoint
class LoginActivity(

) : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            Timber.tag("LOGIN--").d(task.toString())

            try {
                // Google 로그인이 성공하면, Firebase로 인증합니다.
                val account = task.getResult(ApiException::class.java)!!
                Timber.tag("LOGIN--22").d(account.idToken!!)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google 로그인 실패
                Toast.makeText(this, "Google 로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                Timber.tag("LOGIN 실패").d(e.message.toString())
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initFirebaseAuth()
        initViews()
    }

    private fun initViews() {
        binding.loginGoogleBtn.setOnClickListener {
            loginWithGoogle()
        }
    }


    private fun initFirebaseAuth() {
        auth = Firebase.auth

        if (auth.currentUser != null) {
            // 이미 로그인된 사용자
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }


        // GoogleSignInOptions를 구성합니다.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestServerAuthCode(getString(R.string.default_web_client_id))
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // GoogleSignInClient를 초기화합니다.
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        Timber.tag("LOGIN--3").d(idToken)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    val user = auth.currentUser
                    Toast.makeText(this, "환영합니다, ${user?.displayName}!", Toast.LENGTH_SHORT).show()
                    // 여기서 로그인 후 화면 전환 등의 작업을 수행할 수 있습니다.
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // 로그인 실패
                    Toast.makeText(this, "Firebase 인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}