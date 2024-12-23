package kr.ac.konkuk.wastezero.domain.entity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Alarm(
    val id: Int,
    val title: String,
    val time: LocalDateTime,
    val isRead: Boolean,
) {

    companion object {
        const val dateTimeFormatter = "yyyy-MM-dd HH:mm"
        const val timeFormatter = "HH:mm"
        val samples = listOf(
            Alarm(
                1, "식재료 유통기한 알림", LocalDateTime.parse(
                    "2024-12-23 09:00", DateTimeFormatter.ofPattern(
                        kr.ac.konkuk.wastezero.util.date.dateTimeFormatter
                    )
                ), false
            ),
            Alarm(
                1, "식재료 유통기한 알림", LocalDateTime.parse(
                    "2024-12-22 09:00", DateTimeFormatter.ofPattern(
                        kr.ac.konkuk.wastezero.util.date.dateTimeFormatter
                    )
                ), false
            ),
            Alarm(
                1, "식재료 유통기한 알림", LocalDateTime.parse(
                    "2024-12-21 09:00", DateTimeFormatter.ofPattern(
                        kr.ac.konkuk.wastezero.util.date.dateTimeFormatter
                    )
                ), false
            ),
        )
    }
}
