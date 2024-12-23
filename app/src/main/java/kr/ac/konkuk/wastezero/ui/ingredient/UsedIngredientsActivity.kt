package kr.ac.konkuk.wastezero.ui.ingredient

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kr.ac.konkuk.wastezero.databinding.ActivityUsedIngredientBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient
import java.time.LocalDate

class UsedIngredientsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsedIngredientBinding
    private lateinit var adapter: IngredientAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsedIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView 초기화
        setupRecyclerView()

        // Firebase Database 초기화
        database = FirebaseDatabase.getInstance().reference

        // Firebase 데이터 로드
        loadUsedIngredients()
    }

    private fun setupRecyclerView() {
        adapter = IngredientAdapter { ingredient ->
            // 클릭 시 동작 정의 (현재는 필요 없음)
        }
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ingredientsRecyclerView.adapter = adapter
    }

    private fun loadUsedIngredients() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val usedIngredientsRef = database.child("users").child(uid).child("usedIngredients")
        usedIngredientsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ingredientList = mutableListOf<Ingredient>()
                for (data in snapshot.children) {
                    try {
                        // Map 형태로 데이터 읽기
                        val ingredientData = data.value as? Map<String, Any> ?: continue

                        val id = (ingredientData["id"] as? Long)?.toInt() ?: 0
                        val name = ingredientData["name"] as? String ?: "알 수 없는 재료"
                        val imageUrl = ingredientData["imageUrl"] as? String ?: ""
                        val buyingDateMap = ingredientData["buyingDate"] as? Map<String, Any>
                        val expiryDateMap = ingredientData["expiryDate"] as? Map<String, Any>
                        val used = ingredientData["used"] as? Boolean ?: false

                        // 날짜 변환
                        val buyingDate = mapToLocalDate(buyingDateMap)
                        val expiryDate = mapToLocalDate(expiryDateMap)

                        // 모든 필드가 유효한 경우에만 추가
                        if (buyingDate != null && expiryDate != null) {
                            val ingredient = Ingredient(
                                id = id,
                                name = name,
                                imageUrl = imageUrl,
                                buyingDate = buyingDate,
                                expiryDate = expiryDate,
                                used = used
                            )
                            ingredientList.add(ingredient)
                        } else {
                            Toast.makeText(
                                this@UsedIngredientsActivity,
                                "유효하지 않은 날짜 데이터가 포함되어 있습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@UsedIngredientsActivity,
                            "데이터 변환 중 오류 발생: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // RecyclerView에 데이터 전달
                if (ingredientList.isNotEmpty()) {
                    adapter.submitList(ingredientList)
                } else {
                    Toast.makeText(
                        this@UsedIngredientsActivity,
                        "사용한 식재료 데이터가 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@UsedIngredientsActivity,
                    "데이터 로드 실패: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Map 데이터를 LocalDate로 변환하는 함수
    private fun mapToLocalDate(map: Map<String, Any>?): LocalDate? {
        return try {
            if (map == null) return null
            val year = (map["year"] as? Long)?.toInt() ?: return null
            val monthValue = (map["monthValue"] as? Long)?.toInt() ?: return null
            val dayOfMonth = (map["dayOfMonth"] as? Long)?.toInt() ?: return null
            LocalDate.of(year, monthValue, dayOfMonth)
        } catch (e: Exception) {
            println("DEBUG: LocalDate 변환 실패: ${e.message}")
            null
        }
    }

}
