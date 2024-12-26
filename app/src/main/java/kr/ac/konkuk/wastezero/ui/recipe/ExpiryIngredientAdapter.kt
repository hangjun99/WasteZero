package kr.ac.konkuk.wastezero.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kr.ac.konkuk.wastezero.databinding.ItemIngredientMainBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient

class ExpiryIngredientAdapter(
    private val context: Context,
    private val items: List<Ingredient>,
) : RecyclerView.Adapter<ExpiryIngredientAdapter.ExpiryIngredientViewHolder>() {

    private var itemCLickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(item: Ingredient)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpiryIngredientAdapter.ExpiryIngredientViewHolder {
        val binding =
            ItemIngredientMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpiryIngredientViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ExpiryIngredientAdapter.ExpiryIngredientViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        itemCLickListener = listener
    }

    inner class ExpiryIngredientViewHolder(
        private val binding: ItemIngredientMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ingredient) {
            binding.apply {
                ingredientItemTitleTv.text = item.name
                Glide.with(context)
                    .load(item.imageUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(80)))
                    .centerCrop()
                    .into(ingredientItemImageIv)
                itemIngredientCl.setOnClickListener {
                    itemCLickListener?.onClick(item)
                }
            }
        }
    }

}