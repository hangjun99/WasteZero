package kr.ac.konkuk.wastezero.ui.recipe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentRecipeBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient
import kr.ac.konkuk.wastezero.domain.entity.Recipe
import kr.ac.konkuk.wastezero.domain.entity.RecipeDetail
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import kr.ac.konkuk.wastezero.util.navigation.*
import timber.log.Timber
import java.time.LocalDate

class RecipeFragment(

) : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

//    private val firebaseFirestore = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView(getRecipeList())
    }

    private fun getRecipeList(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()

        /*firebaseFirestore.collection("recipe").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Timber.d("${document.id} => ${document.data}")
                    *//*recipes.add(
                        Recipe(
                            1,
                            document.data["name"] as String,
                            document.data["imageUrl"] as String
                        )
                    )*//*
                }
            }
            .addOnFailureListener { result ->
                Timber.d("Error getting documents: $result")
                Toast.makeText(requireContext(), "레시피를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }*/
//        return recipes
        return RecipeDetail.recipeList.map {
            Recipe(it.id, it.name, it.imageUrl, it.recipeUrl)
        }
    }

    private fun setRecyclerView(recipes: List<Recipe>) {
        val result = listOf(example1, example2, exampel3, example4) + recipes.map {
            Timber.d("setRecyclerView: $it")
            RecipeAdapter.ItemType.RecipeItem(it)
        }
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
                        putString(BUNDLE_KEY_RECIPE, item.recipeUrl)
                    })
                }

                override fun onSearchItemClick(item: String) {
                    Timber.d("onSearchItemClick: $item")
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

    }
}