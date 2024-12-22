package kr.ac.konkuk.wastezero.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentMainBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import kr.ac.konkuk.wastezero.util.navigation.*
import timber.log.Timber

class MainFragment(

) : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
    }

    override fun initBinding() {
        super.initBinding()

        binding.apply {
            mainBnv.setupWithNavController(getBottomNavController())

        }

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
                    val data = bundle.getString(BUNDLE_KEY_RECIPE)
                    val action = MainFragmentDirections.actionMainFragmentToRecipeDetailFragment(
                        recipeUrl = bundle.getString(BUNDLE_KEY_RECIPE)!!
                    )
                    controller.navigate(action)
                }

                BUNDLE_KEY_INGREDIENT -> {
                    // TODO: 식재료 상세 창으로
                }
            }
        }
    }

}