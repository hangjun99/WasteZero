package kr.ac.konkuk.wastezero.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.konkuk.wastezero.databinding.ItemStepBinding

class RecipeStepAdapter(
    private val steps: List<Pair<String?, String?>>
) : RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val (description, imageUrl) = steps[position]
        holder.bind(description, imageUrl)
    }

    override fun getItemCount(): Int = steps.size

    inner class StepViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(description: String?, imageUrl: String?) {
            binding.stepDescription.text = description ?: "No description available"
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context).load(imageUrl).into(binding.stepImage)
            } else {
                binding.stepImage.setImageResource(0) // 이미지가 없을 경우 제거
            }
        }
    }
}
