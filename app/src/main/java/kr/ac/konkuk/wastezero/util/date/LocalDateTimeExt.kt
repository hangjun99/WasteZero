package kr.ac.konkuk.wastezero.util.date

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val dateTimeFormatter = "yyyy-MM-dd HH:mm"
const val timeFormatter = "HH:mm"

fun LocalDateTime.toFormattedString(): String {
    val today = LocalDate.now()
    val formatter: DateTimeFormatter = if (this.toLocalDate() != today) {
        DateTimeFormatter.ofPattern(dateTimeFormatter)
    } else {
        DateTimeFormatter.ofPattern(timeFormatter)
    }
    return this.format(formatter)
}