package kr.ac.konkuk.wastezero.ui.recipe

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentRecipeDetailBinding
import kr.ac.konkuk.wastezero.util.base.BaseFragment

class RecipeDetailFragment(

) : BaseFragment<FragmentRecipeDetailBinding>(R.layout.fragment_recipe_detail) {

    private val recipeUrl: String by lazy {
        val args: RecipeDetailFragmentArgs by navArgs()
        args.recipeUrl
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recipeDetailWv.webViewClient = WebViewClient()
            recipeDetailWv.loadUrl(recipeUrl)
        }
    }

}