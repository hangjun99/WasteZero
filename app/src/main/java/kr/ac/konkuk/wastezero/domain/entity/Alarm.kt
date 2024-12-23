package kr.ac.konkuk.wastezero.domain.entity

data class Alarm(
    val id: Int,
    val title: String,
    val time: LocalDateTime,
    val isRead: Boolean,
) {
}
