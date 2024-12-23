package kr.ac.konkuk.wastezero.domain.entity

import java.time.LocalDateTime

data class Alarm(
    val id: Int,
    val title: String,
    val time: LocalDateTime,
    val isRead: Boolean,
) {

    companion object {
        val samples = listOf(
            Alarm(1, "식재료 유통기한 알림", LocalDateTime.now(), false),
            Alarm(1, "식재료 유통기한 알림", LocalDateTime.now(), false),
            Alarm(1, "식재료 유통기한 알림", LocalDateTime.now(), false),
        )
    }
}
