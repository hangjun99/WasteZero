package kr.ac.konkuk.wastezero.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentMainBinding
import kr.ac.konkuk.wastezero.ui.camera.CameraActivity
import kr.ac.konkuk.wastezero.ui.ingredient.InputIngredientsActivity
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import kr.ac.konkuk.wastezero.util.navigation.*
import timber.log.Timber

class MainFragment(

) : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private var isFabOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
    }

    override fun initBinding() {
        super.initBinding()

        binding.apply {
            mainBnv.setupWithNavController(getBottomNavController())
            mainFabIv.setOnClickListener { toggleFabMenu() }
        }

    }

    private fun toggleFabMenu() {
        if (isFabOpen) {
            binding.mainFabIv.setImageResource(R.drawable.ic_add_btn) // + 버튼 이미지
            binding.mainFab.visibility = View.INVISIBLE
        } else {
            binding.mainFabIv.setImageResource(R.drawable.ic_x_btn) // X 버튼 이미지
            binding.mainFab.visibility = View.VISIBLE
            showCustomPopupMenu(binding.mainFabIv)
        }
        isFabOpen = !isFabOpen
    }

    private fun showCustomPopupMenu(anchorView: View) {
        // 팝업 메뉴 레이아웃을 인플레이트
        val popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_menu, null)

        // 팝업 창 생성
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val dimBackground = requireActivity().window.attributes
        dimBackground.alpha = 0.5f // 흐림 정도 (0.0: 투명, 1.0: 불투명)
        requireActivity().window.addFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        requireActivity().window.attributes = dimBackground

        // 버튼 클릭 동작 설정
        popupView.findViewById<LinearLayout>(R.id.camera_input).setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }

        popupView.findViewById<LinearLayout>(R.id.manual_input).setOnClickListener {
            val intent = Intent(requireContext(), InputIngredientsActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }

        popupWindow.setOnDismissListener {
            isFabOpen = false
            binding.mainFabIv.setImageResource(R.drawable.ic_add_btn)
            binding.mainFab.visibility = View.INVISIBLE
            dimBackground.alpha = 1.0f
            requireActivity().window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            requireActivity().window.attributes = dimBackground
        }

        popupWindow.showAsDropDown(anchorView, 50, -650) // 위치 조정
    }

    private fun getBottomNavController() =
        childFragmentManager.findFragmentById(R.id.main_fragment_container_fcv)!!
            .findNavController()

    private fun setNavigation() {

        Timber.d("setNavigation")

        val controller = findNavController()

        childFragmentManager.findFragmentById(R.id.main_fragment_container_fcv)!!.childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_MAIN,
            this
        ) { _, bundle ->
            Timber.d("setFragmentResultListener")
            when (bundle.getString(BUNDLE_KEY_MAIN)) {
                BUNDLE_KEY_SEARCH -> {
                    val action = MainFragmentDirections.actionMainFragmentToSearchFragment(
                        ingredientName = bundle.getString(BUNDLE_KEY_SEARCH)!!
                    )
                    controller.navigate(action)
                }

                BUNDLE_KEY_RECIPE -> {
                    val action = MainFragmentDirections.actionMainFragmentToRecipeDetailFragment(
                        recipeUrl = bundle.getString(BUNDLE_KEY_RECIPE)!!
                    )
                    controller.navigate(action)
                }

                BUNDLE_KEY_INGREDIENT -> {
                    // TODO : 재료 상세 화면으로 이동
                    val action = MainFragmentDirections.actionMainFragmentToAlarmFragment()
                    controller.navigate(action)
                }

                BUNDLE_KEY_ALARM -> {
                    // TODO : 알림 설정 화면으로 이동
                    val action = MainFragmentDirections.actionMainFragmentToAlarmFragment()
                    controller.navigate(action)
                }
            }
        }
    }

    companion object {
        const val NOTIFICATION_REQUEST_KEY_MAIN = "REQUEST_KEY_MAIN"
        const val NOTIFICATION_REQUEST_CODE_MAIN = "REQUEST_CODE_MAIN"
    }

}