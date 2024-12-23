package kr.ac.konkuk.wastezero.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kr.ac.konkuk.wastezero.databinding.ItemRecipeBinding
import kr.ac.konkuk.wastezero.databinding.ItemRecipeTitleBinding
import kr.ac.konkuk.wastezero.domain.entity.Recipe

class RecipeAdapter(
    private val context: Context,
    private val items: List<ItemType>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var recipeClickListener: OnRecipeClickListener? = null

    interface OnRecipeClickListener {
        fun onRecipeItemClick(item: Recipe)
    }

    fun setRecipeClickListener(listener: OnRecipeClickListener) {
        recipeClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val binding = ItemRecipeTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(binding)
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
            is TitleViewHolder -> {
                val titleItem = items[position] as ItemType.TitleItem
                holder.bind(titleItem.title)
            }

            is RecipeViewHolder -> {
                val recipeItem = items[position] as ItemType.RecipeItem
                holder.bind(recipeItem.recipe)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemType.TitleItem -> VIEW_TYPE_TITLE
            is ItemType.RecipeItem -> VIEW_TYPE_RECIPE
        }
    }

    sealed class ItemType {
        data class TitleItem(val title: String) : ItemType()
        data class RecipeItem(val recipe: Recipe) : ItemType()
    }

    inner class TitleViewHolder(private val binding: ItemRecipeTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.titleTv.text = title
        }
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.apply {
                recipeItemTitleTv.text = recipe.name
                Glide.with(context)
                    .load(recipe.imageUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(80)))
                    .centerCrop()
                    .into(recipeItemImageIv)
                recipeItemDetailTv.setOnClickListener {
                    recipeClickListener?.onRecipeItemClick(recipe)
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_TITLE = 0
        private const val VIEW_TYPE_RECIPE = 1
    }
}