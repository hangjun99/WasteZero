package kr.ac.konkuk.wastezero.ui.recipe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentRecipeBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient
import kr.ac.konkuk.wastezero.domain.entity.Recipe
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import kr.ac.konkuk.wastezero.util.navigation.*
import java.time.LocalDate

class RecipeFragment(

) : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeAdapter(requireContext(), result).also {
            it.setItemClickListener(object : RecipeAdapter.OnItemClickListener {
                override fun onIngredientItemClick(item: Ingredient) {
                    setNavigation(Bundle().apply {
                        putString(BUNDLE_KEY_MAIN, BUNDLE_KEY_INGREDIENT)
                        putString(BUNDLE_KEY_INGREDIENT, item.name)
                    })
                }

                override fun onRecipeItemClick(item: Recipe) {
                    setNavigation(Bundle().apply {
                        putString(BUNDLE_KEY_MAIN, BUNDLE_KEY_RECIPE)
                        putString(BUNDLE_KEY_RECIPE, item.name)
                    })
                }

                override fun onSearchItemClick(item: String) {
                    setNavigation(Bundle().apply {
                        putString(BUNDLE_KEY_MAIN, BUNDLE_KEY_SEARCH)
                        putString(BUNDLE_KEY_SEARCH, item)
                    })
                }

            })
        }

        binding.recipeRv.adapter = adapter
        binding.recipeRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setNavigation(data: Bundle) = setFragmentResult(REQUEST_KEY_MAIN, data)

    companion object {
        val ingredient = listOf<Ingredient>(
            Ingredient(
                1,
                "양파",
                "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00=",
                LocalDate.parse("2021-10-01"),
                LocalDate.parse("2021-10-10"),
                false
            ),
            Ingredient(
                1,
                "양파",
                "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00=",
                LocalDate.parse("2021-10-01"),
                LocalDate.parse("2021-10-10"),
                false
            ),
            Ingredient(
                1,
                "양파",
                "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00=",
                LocalDate.parse("2021-10-01"),
                LocalDate.parse("2021-10-10"),
                false
            ),
            Ingredient(
                1,
                "양파",
                "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00=",
                LocalDate.parse("2021-10-01"),
                LocalDate.parse("2021-10-10"),
                false
            ),
        )

        val example1 = RecipeAdapter.ItemType.SearchItem
        val example2 = RecipeAdapter.ItemType.TitleItem("보관기간이 임박한 식재료")
        val exampel3 = RecipeAdapter.ItemType.IngredientItem(ingredient)
        val example4 = RecipeAdapter.ItemType.TitleItem("레시피")
        val example5 = listOf(
            RecipeAdapter.ItemType.RecipeItem(
                Recipe(
                    1,
                    "양파볶음",
                    "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00="
                )
            ),
            RecipeAdapter.ItemType.RecipeItem(
                Recipe(
                    1,
                    "양파볶음",
                    "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00="
                )
            ),
            RecipeAdapter.ItemType.RecipeItem(
                Recipe(
                    1,
                    "양파볶음",
                    "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00="
                )
            ),
            RecipeAdapter.ItemType.RecipeItem(
                Recipe(
                    1,
                    "양파볶음",
                    "https://media.istockphoto.com/id/2148529412/ko/%EC%82%AC%EC%A7%84/%ED%95%9C-%EB%82%A8%EC%9E%90-%EB%86%8D%EB%B6%80%EA%B0%80-%ED%85%83%EB%B0%AD%EC%97%90%EC%84%9C-%EC%96%91%ED%8C%8C%EB%A5%BC-%EC%88%98%ED%99%95%ED%95%98%EA%B3%A0-%EC%9E%88%EB%8B%A4-%EC%84%A0%ED%83%9D%EC%A0%81-%EC%B4%88%EC%A0%90.jpg?s=2048x2048&w=is&k=20&c=6vMP8iHEchYdELxDDMd-jRLK9AZ7m7yInM1lnrvtl00="
                )
            )
        )

        val result = listOf(example1, example2, exampel3, example4) + example5
    }
}