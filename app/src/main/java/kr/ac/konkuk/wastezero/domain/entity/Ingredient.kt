package kr.ac.konkuk.wastezero.domain.entity

import java.util.Date

data class Ingredient(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val buyingDate: Date,
    val expiryDate: Date,
    val isUsed: Boolean,
) {
    fun calculateRestDate(): Int {
        val currentTime = System.currentTimeMillis()
        val restTime = expiryDate.time - currentTime
        return (restTime / (24 * 60 * 60 * 1000)).toInt()
    }
}
