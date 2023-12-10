package com.example.simpletodoapp.utils.extension_functions

import android.content.res.Resources
import java.lang.reflect.Array.get

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx : Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()