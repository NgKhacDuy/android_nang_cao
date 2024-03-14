package com.example.android_advance.utils.common

import java.text.SimpleDateFormat
import java.util.*

class ConvertDateTime {
    fun timeAgo(dtStr: String, locale: Locale = Locale("vi", "VN")): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        val date = formatter.parse(dtStr)
        val now = Date()

        val delta = now.time - date.time

        // Vietnamese unit translations (modify as needed)
        val units = arrayOf(
            Pair("năm", 31536000000L),
            Pair("tháng", 2592000000L),
            Pair("tuần", 604800000L),
            Pair("ngày", 86400000L),
            Pair("giờ", 3600000L),
            Pair("phút", 60000L),
            Pair("giây", 1000L)
        )

        // Find the most suitable unit
        for (unit in units) {
            val threshold = unit.second
            if (delta >= threshold) {
                val value = (delta / threshold).toInt()
                val unitStr = unit.first + (if (value > 1) "" else "")
                return "$value $unitStr trước" // Customize formatting for Vietnamese
            }
        }

        return "vừa mới" // "vừa mới" for "just now" in Vietnamese
    }
}