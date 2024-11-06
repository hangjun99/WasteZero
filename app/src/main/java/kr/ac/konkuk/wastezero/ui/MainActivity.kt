// MainActivity.kt
package kr.ac.konkuk.wastezero.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ActivityMainBinding
import kr.ac.konkuk.wastezero.ui.profile.ProfileFragment
import kr.ac.konkuk.wastezero.util.base.BaseActivity

//@AndroidEntryPoint
class MainActivity(

) : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 초기 화면 설정 (메인 화면)
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
        }
    }

    // Fragment 전환 함수
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
    }
}