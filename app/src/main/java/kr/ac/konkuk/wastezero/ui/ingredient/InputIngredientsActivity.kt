package kr.ac.konkuk.wastezero.ui.ingredient

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ActivityInputIngredientBinding
import kr.ac.konkuk.wastezero.domain.entity.Ingredient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class InputIngredientsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputIngredientBinding
    private lateinit var ingredientAdapter: ArrayAdapter<String>
    private val databaseRef = FirebaseDatabase.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val aiRecognizableIngredients = mapOf(
        "beef" to "소고기",
        "cabbage" to "양배추",
        "carrot" to "당근",
        "chicken" to "닭고기",
        "cucumber" to "오이",
        "egg" to "달걀",
        "enoki mushroom" to "팽이버섯",
        "garlic" to "마늘",
        "onion" to "양파",
        "pepper" to "고추",
        "pork" to "돼지고기",
        "potato" to "감자",
        "radish" to "무",
        "salmon" to "연어",
        "shrimp" to "새우",
        "tomato" to "토마토",
        "tahu" to "두부"
    )

    private val ingredientImageMap = mapOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전달받은 데이터 처리
        val isNewIngredient = intent.getBooleanExtra("isNewIngredient", true)
        val ingredientName = intent.getStringExtra("ingredientName") ?: ""
        val buyingDateStr = intent.getStringExtra("buyingDate") ?: LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val expiryDateStr = intent.getStringExtra("expiryDate") ?: LocalDate.now().plusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val hideUsageText = intent.getBooleanExtra("hideUsageText", true)

        // 초기화
        binding.ingredientName.setText(ingredientName)
        binding.purchaseDate.setText(buyingDateStr)
        binding.expirationDays.setText(expiryDateStr)

        if (hideUsageText) {
            binding.checkboxUsed.visibility = View.GONE
            binding.textviewUsed.visibility = View.GONE
        } else {
            binding.checkboxUsed.visibility = View.VISIBLE
        }

        setupAutoCompleteTextView()
        setupDatePicker()
        setupShelfLifeOptions()

        // 저장 버튼 클릭 이벤트
        binding.savebutton.setOnClickListener {
            saveIngredient(isNewIngredient)
        }
    }

    private fun setupAutoCompleteTextView() {
        val koreanIngredients = aiRecognizableIngredients.values.toList()
        ingredientAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, koreanIngredients)
        binding.ingredientName.setAdapter(ingredientAdapter)

        binding.ingredientName.setOnItemClickListener { _, _, position, _ ->
            val selectedIngredient = ingredientAdapter.getItem(position) ?: ""
            updateIngredientImage(selectedIngredient)
        }

        binding.ingredientName.setOnClickListener {
            if (binding.ingredientName.text.isEmpty()) {
                binding.ingredientName.showDropDown()
            }
        }
    }

    private fun updateIngredientImage(ingredientName: String) {
        val resourceId = ingredientImageMap[ingredientName]
        if (resourceId != null) {
            binding.ingredientImage.setImageResource(resourceId)
        } else {
            binding.ingredientImage.setImageResource(R.drawable.ic_logo)
        }
    }

    private fun setupDatePicker() {
        binding.purchaseDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                val selectedDate = LocalDate.of(year, month + 1, day)
                binding.purchaseDate.setText(selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }
    }

    private fun setupShelfLifeOptions() {
        val shelfLifeOptions = listOf("5일", "10일", "30일")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, shelfLifeOptions)
        binding.expirationDays.setAdapter(adapter)
        binding.expirationDays.setOnClickListener {
            binding.expirationDays.showDropDown()
        }
    }

    private fun saveIngredient(isNewIngredient: Boolean) {
        val name = binding.ingredientName.text.toString()
        val buyingDate = LocalDate.parse(binding.purchaseDate.text.toString(), DateTimeFormatter.ISO_LOCAL_DATE)
        val expiryDays = binding.expirationDays.text.toString().replace("일", "").toIntOrNull() ?: 10
        val expiryDate = buyingDate.plusDays(expiryDays.toLong())
        val used = binding.checkboxUsed.isChecked

        if (name.isBlank()) {
            Toast.makeText(this, "식재료 이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val newIngredient = Ingredient(
            id = 0,
            name = name,
            imageUrl = "",
            buyingDate = buyingDate,
            expiryDate = expiryDate,
            used = used
        )

        val userIngredientsRef = databaseRef.child("users").child(userId).child("ingredients")
        val usedIngredientsRef = databaseRef.child("users").child(userId).child("usedIngredients")

        if (isNewIngredient) {
            // 새 식재료 추가
            val ingredientId = userIngredientsRef.push().key
            if (ingredientId != null) {
                userIngredientsRef.child(ingredientId).setValue(newIngredient)
                    .addOnSuccessListener {
                        Toast.makeText(this, "식재료가 추가되었습니다!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "식재료 추가 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            // 기존 식재료 업데이트
            val ingredientQuery = userIngredientsRef.orderByChild("name").equalTo(name)
            ingredientQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (child in snapshot.children) {
                            // Firebase 데이터 업데이트
                            val ingredientId = child.key
                            if (used) {
                                // 사용한 식재료로 이동
                                usedIngredientsRef.child(ingredientId!!).setValue(newIngredient)
                                    .addOnSuccessListener {
                                        userIngredientsRef.child(ingredientId).removeValue()
                                        Toast.makeText(this@InputIngredientsActivity, "식재료가 사용한 목록으로 이동되었습니다.", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this@InputIngredientsActivity, "사용한 식재료 이동 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                // 보유한 식재료만 업데이트
                                userIngredientsRef.child(ingredientId!!).setValue(newIngredient)
                                    .addOnSuccessListener {
                                        Toast.makeText(this@InputIngredientsActivity, "식재료가 업데이트되었습니다!", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this@InputIngredientsActivity, "식재료 업데이트 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(this@InputIngredientsActivity, "해당 식재료를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@InputIngredientsActivity, "데이터 검색 실패: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
