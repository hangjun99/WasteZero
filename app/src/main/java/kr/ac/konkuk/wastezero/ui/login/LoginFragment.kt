package kr.ac.konkuk.wastezero.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentLoginBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import timber.log.Timber

class LoginFragment(

) : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

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
                Toast.makeText(requireContext(), "Google 로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                Timber.tag("LOGIN 실패").d(e.message.toString())
            }

        }

    private var lastBackPressedTime = 0L
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (lastBackPressedTime > System.currentTimeMillis() - 2000) {
                    requireActivity().finish()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "뒤로가기 버튼을 한번 더 누르면 앱이 종료됩니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    lastBackPressedTime = System.currentTimeMillis()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")
        initFirebaseAuth()
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }


    override fun initBinding() {
        super.initBinding()

        binding.loginGoogleBtn.setOnClickListener {
            loginWithGoogle()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun initFirebaseAuth() {
        auth = Firebase.auth

        if (auth.currentUser != null) {
            // 이미 로그인된 사용자
            successLogin()
            return
        }


        // GoogleSignInOptions를 구성합니다.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // GoogleSignInClient를 초기화합니다.
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        Timber.tag("LOGIN--3").d(idToken)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    val user = auth.currentUser
                    Toast.makeText(
                        requireContext(),
                        "환영합니다, ${user?.displayName}!",
                        Toast.LENGTH_SHORT
                    ).show()

                    successLogin()
                } else {
                    Toast.makeText(requireContext(), "Firebase 인증에 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun successLogin() {
        findNavController().popBackStack()
        return
    }

}