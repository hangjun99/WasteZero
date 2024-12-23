package kr.ac.konkuk.wastezero.ui.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ItemIngredientBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient

class IngredientAdapter(
    private val onItemClicked: (Ingredient) -> Unit
) : ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    private val ingredientImageView = mapOf(
        "소고기" to R.drawable.beef,
        "양배추" to R.drawable.cabbage,
        "당근" to R.drawable.carrot,
        "닭고기" to R.drawable.chicken,
        "오이" to R.drawable.cucumber,
        "달걀" to R.drawable.egg,
        "팽이버섯" to R.drawable.enoki_mushroom,
        "마늘" to R.drawable.garlic,
        "양파" to R.drawable.onion,
        "고추" to R.drawable.pepper,
        "돼지고기" to R.drawable.pork,
        "감자" to R.drawable.potato,
        "무" to R.drawable.radish,
        "연어" to R.drawable.salmon,
        "새우" to R.drawable.shrimp,
        "토마토" to R.drawable.tomato,
        "두부" to R.drawable.tahu
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
    }

    inner class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            binding.ingredientName.text = ingredient.name

            val imageResId = ingredientImageView[ingredient.name]
            if(imageResId != null){
                binding.ingredientImage.setImageResource(imageResId)
            }   else{
                binding.ingredientImage.setImageResource(R.drawable.ic_logo)
            }
            // 전체 항목 클릭 이벤트
            binding.root.setOnClickListener {
                onItemClicked(ingredient)
            }
        }
    }
}

class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }
}
