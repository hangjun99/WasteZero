// ProfileFragment.kt
package kr.ac.konkuk.wastezero.ui.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentProfileBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuList = listOf("보유한 식재료", "사용한 식재료", "알림 변경", "닉네임 변경", "로그아웃", "회원탈퇴")

        // RecyclerView의 LayoutManager 설정 추가
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMenu.adapter = ProfileMenuAdapter(childFragmentManager, menuList)
    }
}