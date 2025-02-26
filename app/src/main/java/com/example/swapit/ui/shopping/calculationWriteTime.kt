package com.example.swapit.ui.shopping

import android.icu.util.Calendar
import java.util.concurrent.TimeUnit

import java.text.SimpleDateFormat
import java.util.*

fun calculationWriteTime(createDateTime: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val createDate: Date = dateFormat.parse(createDateTime) ?: return "알 수 없음"

    val createDateTimeMillis = createDate.time
    val nowDateTimeMillis = Calendar.getInstance().timeInMillis

    val differenceValue = nowDateTimeMillis - createDateTimeMillis
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
