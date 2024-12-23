package kr.ac.konkuk.wastezero.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kr.ac.konkuk.wastezero.databinding.ItemRecipeBinding
import kr.ac.konkuk.wastezero.databinding.ItemRecipeIngredientListBinding
import kr.ac.konkuk.wastezero.databinding.ItemRecipeTitleBinding
import kr.ac.konkuk.wastezero.databinding.ItemSearchBarBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient
import kr.ac.konkuk.wastezero.domain.entity.Recipe
import timber.log.Timber

class RecipeAdapter(
    private val context: Context,
    private val items: List<ItemType>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SEARCH -> {
                val binding = ItemSearchBarBinding.inflate(inflater, parent, false)
                SearchViewHolder(binding)
            }

            VIEW_TYPE_TITLE -> {
                val binding = ItemRecipeTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(binding)
            }

            VIEW_TYPE_INGREDIENT -> {
                val binding = ItemRecipeIngredientListBinding.inflate(inflater, parent, false)
                IngredientViewHolder(binding)
            }

            VIEW_TYPE_RECIPE -> {
                val binding = ItemRecipeBinding.inflate(inflater, parent, false)
                RecipeViewHolder(binding)
            }

            else -> {
                val binding = ItemRecipeBinding.inflate(inflater, parent, false)
                RecipeViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> holder.bind()
            is TitleViewHolder -> {
                val titleItem = items[position] as ItemType.TitleItem
                holder.bind(titleItem.title)
            }

            is IngredientViewHolder -> {
                val ingredientItem = items[position] as ItemType.IngredientItem
                holder.bind(ingredientItem.ingredients)
            }

            is RecipeViewHolder -> {
                val recipeItem = items[position] as ItemType.RecipeItem
                holder.bind(recipeItem.recipe)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemType.SearchItem -> VIEW_TYPE_SEARCH
            is ItemType.TitleItem -> VIEW_TYPE_TITLE
            is ItemType.IngredientItem -> VIEW_TYPE_INGREDIENT
            is ItemType.RecipeItem -> VIEW_TYPE_RECIPE
        }
    }

    interface OnItemClickListener {
        fun onIngredientItemClick(item: Ingredient)
        fun onRecipeItemClick(item: Recipe)
        fun onSearchItemClick(item: String)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class SearchViewHolder(
        private val binding: ItemSearchBarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
//                searchBarWindowEt.

                searchBarBtnLl.setOnClickListener {
                    itemClickListener?.onSearchItemClick(searchBarWindowEt.text.toString())
                }
            }
        }
    }

    inner class TitleViewHolder(
        private val binding: ItemRecipeTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.apply {
                titleTv.text = title
            }
        }
    }

    inner class IngredientViewHolder(
        private val binding: ItemRecipeIngredientListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredients: List<Ingredient>) {
            val adapter = ExpiryIngredientAdapter(context, ingredients).apply {
                setItemClickListener(object : ExpiryIngredientAdapter.OnItemClickListener {
                    override fun onClick(item: Ingredient) {
                        itemClickListener?.onIngredientItemClick(item)
                    }
                })
            }

            binding.apply {
                recipeExpiaryIngredientListRv.adapter = adapter
            }
        }
    }

    inner class RecipeViewHolder(
        private val binding: ItemRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.apply {
                recipeItemTitleTv.text = recipe.name
                Glide.with(context)
                    .load(recipe.imageUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(80)))
                    .centerCrop()
                    .into(recipeItemImageIv)
                recipeItemDetailTv.setOnClickListener {
                    itemClickListener?.onRecipeItemClick(recipe)
                }
            }
        }
    }

    sealed class ItemType {
        data object SearchItem : ItemType()
        data class TitleItem(val title: String) : ItemType()
        data class IngredientItem(val ingredients: List<Ingredient>) : ItemType()
        data class RecipeItem(val recipe: Recipe) : ItemType()
    }

    companion object {
        private const val VIEW_TYPE_SEARCH = 0
        private const val VIEW_TYPE_TITLE = 1
        private const val VIEW_TYPE_INGREDIENT = 2
        private const val VIEW_TYPE_RECIPE = 3
    }
}