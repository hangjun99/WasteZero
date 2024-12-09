package kr.ac.konkuk.wastezero.ui.recipe

import android.os.Bundle
import android.view.View
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentRecipeBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment

class RecipeFragment(

) : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeAdapter(requireContext(), result)
        binding.recipeRv.adapter = adapter
        binding.recipeRv.layoutManager = LinearLayoutManager(requireContext())

    }
}