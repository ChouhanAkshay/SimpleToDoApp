package com.example.simpletodoapp.utils.helper

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.simpletodoapp.ui.theme.listOfCircularProgressColor
import com.example.simpletodoapp.utils.DateUtils

object GeneralHelper {
    internal fun getMonthlyTaskCompletionProgress(
        numberOfDaysTaskCompleted : Int
    ) : Int {
        return ((numberOfDaysTaskCompleted.toFloat() / DateUtils.getNumberOfDaysInCurrentMonth()) * 100).toInt()
    }

    fun getRandomMidTonColor() : Color = listOfCircularProgressColor.random()
 }