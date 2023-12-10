package com.example.simpletodoapp.utils

import android.annotation.SuppressLint
import androidx.compose.material3.CalendarLocale
import java.text.SimpleDateFormat
import java.util.Calendar

object DateUtils {
    fun getNumberOfDaysInCurrentMonth() : Int {
        return Calendar.getInstance().getActualMaximum(Calendar.DATE)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate() : String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(Calendar.getInstance().time)
    }
}