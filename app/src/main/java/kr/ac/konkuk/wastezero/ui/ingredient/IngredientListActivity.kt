package kr.ac.konkuk.wastezero.ui.ingredient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.konkuk.wastezero.databinding.ActivityIngredientListBinding

class IngredientListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientListBinding
    private lateinit var ingredientListAdapter: IngredientListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent로부터 데이터 수신
        val detectedIngredients = intent.getStringArrayListExtra("detectedIngredients") ?: listOf()

        Log.d("IngredientListActivity", "Received Ingredients: $detectedIngredients")

        // RecyclerView 초기화
        setupRecyclerView(detectedIngredients)
    }

    private fun setupRecyclerView(ingredients: List<String>) {
        ingredientListAdapter = IngredientListAdapter { ingredient ->
            val intent = Intent(this, InputIngredientsActivity::class.java).apply {
                putExtra("ingredientName", ingredient)
            }
            startActivity(intent)
        }

        binding.ingredientRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@IngredientListActivity)
            adapter = ingredientListAdapter
        }

        ingredientListAdapter.submitList(ingredients)
    }
}
