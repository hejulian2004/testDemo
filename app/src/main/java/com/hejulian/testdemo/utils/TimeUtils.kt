package com.hejulian.testdemo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {

    fun formatTime(
        timeMillis: Long,
        pattern: String = "yyyy-MM-dd HH:mm"
    ): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timeMillis))
    }
}