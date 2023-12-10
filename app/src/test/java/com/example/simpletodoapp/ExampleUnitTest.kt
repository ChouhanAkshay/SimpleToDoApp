package com.example.simpletodoapp

import com.example.simpletodoapp.utils.DateUtils
import com.example.simpletodoapp.utils.helper.GeneralHelper
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun verifyNumberOfDaysInCurrentMonth(){
        assertEquals(31, DateUtils.getNumberOfDaysInCurrentMonth())
    }

    @Test
    fun getTaskCompletionProgress() {
        assertEquals(48, GeneralHelper.getMonthlyTaskCompletionProgress(15))
    }
}