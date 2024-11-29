package kr.ac.konkuk.wastezero.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ActivityMainBinding
import kr.ac.konkuk.wastezero.util.base.BaseActivity
import timber.log.Timber

//@AndroidEntryPoint
class MainActivity(

) : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*// 초기 화면 설정 (메인 화면)
        openFragment(null)

        // Bottom Navigation 아이템 선택 이벤트
        binding.mainBnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_recipe -> {
                    // 메인 화면으로 전환
                    openFragment(null)
                    true
                }

                R.id.menu_profile -> {
                    // ProfileFragment로 전환
                    openFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }*/

        Timber.d("onCreate")
        checkLoginState(Firebase.auth.currentUser != null)
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

        val controller = getNavigationController()

        binding.apply {

        }

    }

    private fun checkLoginState(isLogin: Boolean) {
        if (!isLogin) {
            getNavigationController().navigate(R.id.action_mainFragment_to_login_graph)
        }

    }

    private fun getNavigationController(): NavController =
        supportFragmentManager.findFragmentById(R.id.main_navigation_host_fcv)!!.findNavController()

    /*// Fragment 전환 함수
    private fun openFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()

        if (fragment == null) {
            // 메인 화면으로 돌아갈 때는 Fragment를 제거
            supportFragmentManager.fragments.forEach { transaction.remove(it) }
        } else {
            // ProfileFragment 추가
            transaction.replace(binding.containerFl.id, fragment)
        }

        transaction.commit()
    }*/
}