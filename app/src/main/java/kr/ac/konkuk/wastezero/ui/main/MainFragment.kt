package kr.ac.konkuk.wastezero.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
            mainFabIv.setOnClickListener { toggleFabMenu(mainFab) }
        }

    }

    private fun toggleFabMenu(fab: FloatingActionButton) {
        if (isFabOpen) {
            fab.setImageResource(R.drawable.addbutton) // + 이미지
        } else {
            fab.setImageResource(R.drawable.xbutton) // - 이미지
            showPopupMenu(fab)
        }
        isFabOpen = !isFabOpen
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.fab_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_camera_input -> {
                    /*findNavController().navigate(R.id.action_mainFragment_to_cameraFragment)
                    true*/
                    val intent = Intent(requireContext(), CameraActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_direct_input -> {
                    val intent = Intent(requireContext(), InputIngredientsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        popupMenu.setOnDismissListener {
            isFabOpen = false
            val fab = binding.mainFab
            fab.setImageResource(R.drawable.addbutton) // + 이미지로 초기화
        }
        popupMenu.show()
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