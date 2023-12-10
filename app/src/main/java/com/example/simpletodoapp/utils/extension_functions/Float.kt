package com.example.simpletodoapp.utils.extension_functions

import android.content.res.Resources

val Float.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()