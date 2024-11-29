package kr.ac.konkuk.wastezero.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentMainBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment

class MainFragment(

) : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initBinding() {
        super.initBinding()

        binding.apply {
            val navController =
                childFragmentManager.findFragmentById(R.id.main_fragment_container_fcv)!!
                    .findNavController()
            mainBnv.setupWithNavController(navController)

        }

//        findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
    }

}