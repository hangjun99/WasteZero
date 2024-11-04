// MainActivity.kt
package kr.ac.konkuk.wastezero.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.ac.konkuk.wastezero.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // 초기 화면 설정 (메인 화면)
        openFragment(null)

        // Bottom Navigation 아이템 선택 이벤트
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
            transaction.replace(R.id.fragment_container, fragment)
        }

        transaction.commit()
    }
}