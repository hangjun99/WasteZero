package kr.ac.konkuk.wastezero.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.setFragmentResultListener
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
        val fab:FloatingActionButton = binding.mainFab
        fab.setOnClickListener{
            toggleFabMenu(fab)
        }
        setNavigation()
    }

    override fun initBinding() {
        super.initBinding()

        binding.apply {
            mainBnv.setupWithNavController(getBottomNavController())

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
                BUNDLE_KEY_SEARCH -> controller.navigate(R.id.action_mainFragment_to_searchFragment)

                BUNDLE_KEY_RECIPE -> controller.navigate(R.id.action_mainFragment_to_recipeDetailFragment)

                BUNDLE_KEY_INGREDIENT -> {
                    // TODO: 식재료 상세 창으로
                }
            }
        }
    }

}