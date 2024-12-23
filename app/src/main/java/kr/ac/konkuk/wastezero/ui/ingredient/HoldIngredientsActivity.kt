package kr.ac.konkuk.wastezero.ui.ingredient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kr.ac.konkuk.wastezero.databinding.ActivitiyHoldIngredientBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HoldIngredientsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitiyHoldIngredientBinding
    private lateinit var ingredientAdapter: IngredientAdapter
    private val databaseRef = FirebaseDatabase.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitiyHoldIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadIngredients()
    }

    private fun setupRecyclerView() {
        ingredientAdapter = IngredientAdapter { ingredient ->
            val intent = Intent(this, InputIngredientsActivity::class.java).apply {
                putExtra("isNewIngredient", false) // 기존 식재료임을 명시
                putExtra("ingredientName", ingredient.name)
                putExtra("buyingDate", ingredient.buyingDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                putExtra("expiryDate", ingredient.expiryDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                putExtra("hideUsageText", false) // 사용유무 텍스트 표시
            }
            startActivity(intent)
        }

        binding.ingredientsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HoldIngredientsActivity)
            adapter = ingredientAdapter
        }
    }

    private fun loadIngredients() {
        if (userId == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val ingredientsRef = databaseRef.child("users").child(userId).child("ingredients")
        ingredientsRef.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                Toast.makeText(this, "식재료 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            snapshot.children.forEach { child ->
                val childData = child.value
                println("DEBUG: Firebase 데이터: $childData")
            }

            val ingredientList = snapshot.children.mapNotNull { child ->
                val id = child.key // Firebase 키를 String으로 사용
                val name = child.child("name").value as? String
                val buyingDateMap = child.child("buyingDate").value as? Map<String, Any>
                val expiryDateMap = child.child("expiryDate").value as? Map<String, Any>
                val used = child.child("used").value as? Boolean ?: false

                val buyingDate = buyingDateMap?.let {
                    try {
                        mapToLocalDate(it)
                    } catch (e: Exception) {
                        println("DEBUG: buyingDate 변환 실패: $e")
                        null
                    }
                }

                val expiryDate = expiryDateMap?.let {
                    try {
                        mapToLocalDate(it)
                    } catch (e: Exception) {
                        println("DEBUG: expiryDate 변환 실패: $e")
                        null
                    }
                }

                if (id != null && name != null && buyingDate != null && expiryDate != null) {
                    Ingredient(
                        id = id.hashCode(), // 키를 해시코드로 변환하여 Int로 사용
                        name = name,
                        imageUrl = "",
                        buyingDate = buyingDate,
                        expiryDate = expiryDate,
                        used = used
                    )
                } else {
                    println("DEBUG: 매핑 실패: $child")
                    null
                }
            }

            if (ingredientList.isEmpty()) {
                println("DEBUG: 변환된 데이터 리스트가 비어있음")
                Toast.makeText(this, "식재료를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }

            ingredientAdapter.submitList(ingredientList)
        }.addOnFailureListener {
            println("DEBUG: Firebase 데이터 로드 실패 - ${it.message}")
            Toast.makeText(this, "데이터를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mapToLocalDate(map: Map<String, Any>): LocalDate? {
        try {
            val year = (map["year"] as? Long)?.toInt() ?: return null
            val monthValue = (map["monthValue"] as? Long)?.toInt() ?: return null
            val dayOfMonth = (map["dayOfMonth"] as? Long)?.toInt() ?: return null
            return LocalDate.of(year, monthValue, dayOfMonth)
        } catch (e: Exception) {
            println("DEBUG: mapToLocalDate 변환 실패: $e")
            return null
        }
    }
}
