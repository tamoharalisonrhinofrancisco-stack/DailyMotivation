package com.rhino.dailymotivation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun todayDateLabel(): String {
        val formatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        return formatter.format(Date())
    }

    fun todayKey(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }
}
