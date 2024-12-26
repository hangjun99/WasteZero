package kr.ac.konkuk.wastezero.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentProfileBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import kr.ac.konkuk.wastezero.util.navigation.BUNDLE_KEY_ALARM
import kr.ac.konkuk.wastezero.util.navigation.BUNDLE_KEY_INGREDIENT
import kr.ac.konkuk.wastezero.util.navigation.BUNDLE_KEY_MAIN
import kr.ac.konkuk.wastezero.util.navigation.REQUEST_KEY_MAIN

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 메뉴 설정
        val menuList = listOf("보유한 식재료", "사용한 식재료", "알림 변경", "닉네임 변경", "로그아웃", "회원탈퇴")
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMenu.adapter = ProfileMenuAdapter(requireContext(), menuList).apply {
            setOnItemClickListener(object : ProfileMenuAdapter.OnItemClickListener {
                override fun onAlarmItemClick() {
                    // 알림 변경
                    setNavigation(Bundle().apply {
                        putString(BUNDLE_KEY_MAIN, BUNDLE_KEY_ALARM)
                    })
                }
            })
        }

        // Firebase 사용자 정보로 프로필 UI 업데이트
        updateProfileUI()
    }

    private fun updateProfileUI() {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid

        if (user != null && isAdded && view != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId!!)

            // 닉네임 가져오기 또는 초기화
            userRef.child("nickname").get().addOnSuccessListener { dataSnapshot ->
                if (isAdded && view != null) {
                    val nickname = dataSnapshot.getValue(String::class.java)
                    binding.profileNickname.text = nickname ?: user.displayName ?: "사용자"
                }
            }.addOnFailureListener {
                if (isAdded && view != null) {
                    binding.profileNickname.text = user.displayName ?: "사용자"
                }
            }

            // 프로필 이미지 업데이트
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.profile_circle_background)
                .into(binding.profileCircle)
        }
    }

    private fun setNavigation(data: Bundle) = setFragmentResult(REQUEST_KEY_MAIN, data)

    override fun onDestroyView() {
        super.onDestroyView()
        // 추가적으로 필요한 정리 작업이 있다면 여기에 추가
    }
}
