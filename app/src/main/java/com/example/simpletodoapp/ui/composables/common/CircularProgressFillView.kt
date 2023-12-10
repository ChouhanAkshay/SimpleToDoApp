package com.example.simpletodoapp.ui.composables.common

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.ui.theme.SimpleToDoAppTheme
import com.example.simpletodoapp.utils.extension_functions.dpToPx
import com.example.simpletodoapp.utils.extension_functions.pxToDp
import java.util.Random

@Composable
fun CircularProgressFillView(
    progress: Float,
    color: Color = Color.Red,
    backgroundColor: Color = Color.Transparent,
    modifier: Modifier
) {
    var width by remember { mutableIntStateOf(0) }
    var height by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .onGloballyPositioned
            {
                width = it.size.width
                height = it.size.height
            }
    )
    {

        Canvas(modifier = Modifier.offset(0.dp, 0.dp)) {
            drawCircle(
                color = color,
                radius = ((width + height) * progress) / 100
            )
        }
    }
}

@Preview
@Composable
fun previewCircularProgressFillViewprogress() {
    SimpleToDoAppTheme {
        CircularProgressFillView(
            progress = 0f,
            modifier = Modifier.fillMaxSize()
        )
    }
}