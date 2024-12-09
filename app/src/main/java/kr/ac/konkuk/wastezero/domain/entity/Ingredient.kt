package kr.ac.konkuk.wastezero.domain.entity

import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

data class Ingredient(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val buyingDate: LocalDate,
    val expiryDate: LocalDate,
    val isUsed: Boolean,
) {
    fun calculateRestDate(): Int {
        val currentDay = LocalDate.now()
        return if (!isExpired()) ChronoUnit.DAYS.between(currentDay, expiryDate).toInt() else -1
    }

    fun isExpired(): Boolean {
        // 현재 날짜가 유통기한 날짜보다 뒤라면 T / 아직 전이거나 당일이면 F
        val currentDate = LocalDate.now()
        return currentDate.isAfter(expiryDate)
    }
}
