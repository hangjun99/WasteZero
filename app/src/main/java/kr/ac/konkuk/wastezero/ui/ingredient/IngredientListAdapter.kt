package kr.ac.konkuk.wastezero.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.wastezero.R

class IngredientListAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {

    private val ingredients = mutableListOf<String>()

    fun submitList(newIngredients: List<String>) {
        ingredients.clear()
        ingredients.addAll(newIngredients)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size

    inner class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientName: TextView = view.findViewById(R.id.ingredientName)

        fun bind(ingredient: String) {
            ingredientName.text = ingredient
            itemView.setOnClickListener { onClick(ingredient) }
        }
    }
}
