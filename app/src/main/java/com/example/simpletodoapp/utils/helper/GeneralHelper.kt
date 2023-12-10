package com.example.simpletodoapp.utils.helper

import com.example.simpletodoapp.utils.DateUtils

object GeneralHelper {
    internal fun getMonthlyTaskCompletionProgress(
        numberOfDaysTaskCompleted : Int
    ) : Int {
        return ((numberOfDaysTaskCompleted.toFloat() / DateUtils.getNumberOfDaysInCurrentMonth()) * 100).toInt()
    }
 }