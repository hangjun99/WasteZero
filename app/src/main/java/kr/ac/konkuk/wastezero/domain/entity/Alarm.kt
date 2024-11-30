package kr.ac.konkuk.wastezero.domain.entity

data class Alarm(
    val id: Int,
    val title: String,
    val time: String,
    val isRead: Boolean,
)
