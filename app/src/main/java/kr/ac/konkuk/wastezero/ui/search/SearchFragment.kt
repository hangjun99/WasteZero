package kr.ac.konkuk.wastezero.ui.search

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentSearchBinding
import kr.ac.konkuk.wastezero.domain.entity.Recipe
import kr.ac.konkuk.wastezero.domain.entity.RecipeDetail
import kr.ac.konkuk.wastezero.util.base.BaseFragment

class SearchFragment(

) : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val args: SearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchTerm = args.ingredientName
        setRecyclerView(getRecipeList(searchTerm))
    }

    private fun getRecipeList(term: String): List<RecipeAdapter.ItemType> {
        val title = RecipeAdapter.ItemType.TitleItem("\"$term\"를 포함한 레시피")
        // TODO : DB에서 term을 포함한 레시피 리스트를 가져오는 코드 작성
        val recipes = mutableListOf<RecipeAdapter.ItemType.RecipeItem>()
        when (term) {
            "감자" -> {
                RecipeDetail.potatoList.map {
                    recipes.add(
                        RecipeAdapter.ItemType.RecipeItem(
                            Recipe(it.id, it.name, it.imageUrl, it.recipeUrl)
                        )
                    )
                }
            }

            "두부" -> {
                RecipeDetail.tofuList.map {
                    recipes.add(
                        RecipeAdapter.ItemType.RecipeItem(
                            Recipe(it.id, it.name, it.imageUrl, it.recipeUrl)
                        )
                    )
                }
            }

            "돼지고기" -> {
                RecipeDetail.porkList.map {
                    recipes.add(
                        RecipeAdapter.ItemType.RecipeItem(
                            Recipe(it.id, it.name, it.imageUrl, it.recipeUrl)
                        )
                    )
                }
            }

            "돼지" -> {
                RecipeDetail.porkList.map {
                    recipes.add(
                        RecipeAdapter.ItemType.RecipeItem(
                            Recipe(it.id, it.name, it.imageUrl, it.recipeUrl)
                        )
                    )
                }
            }
        }

        return listOf(title) + recipes
    }

    private fun setRecyclerView(result: List<RecipeAdapter.ItemType>) {
        val adapter = RecipeAdapter(requireContext(), result).also {
            it.setRecipeClickListener(object : RecipeAdapter.OnRecipeClickListener {
                override fun onRecipeItemClick(item: Recipe) {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(
                            // TODO : item의 recipeUrl을 넘겨주는 코드 작성
                            recipeUrl = item.recipeUrl
                        )
                    findNavController().navigate(action)
                }
            })
        }

        binding.searchRecipeListRv.adapter = adapter
        binding.searchRecipeListRv.layoutManager = LinearLayoutManager(requireContext())
    }

}