package com.example.swapit.ui.shopping

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

fun calculateTime(createDateTime: String): String {
    val now = LocalDateTime.now()
    val convertTime = LocalDateTime.parse(createDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    val differenceValue = ChronoUnit.MILLIS.between(convertTime, now)
    return when {
        differenceValue < 60000 -> "방금 전"
        differenceValue < 3600000 -> "${TimeUnit.MILLISECONDS.toMinutes(differenceValue)}분 전"
        differenceValue < 86400000 -> "${TimeUnit.MILLISECONDS.toHours(differenceValue)}시간 전"
        differenceValue < 604800000 -> "${TimeUnit.MILLISECONDS.toDays(differenceValue)}일 전"
        differenceValue < 2419200000 -> "${TimeUnit.MILLISECONDS.toDays(differenceValue) / 7}주 전"
        differenceValue < 31556952000 -> "${TimeUnit.MILLISECONDS.toDays(differenceValue) / 30}개월 전"
        else -> "${TimeUnit.MILLISECONDS.toDays(differenceValue) / 365}년 전"
    }
}
