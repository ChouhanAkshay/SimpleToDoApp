package com.example.simpletodoapp.ui.composables.common

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.ui.theme.SimpleToDoAppTheme
import com.example.simpletodoapp.utils.extension_functions.dpToPx
import com.example.simpletodoapp.utils.extension_functions.pxToDp
import java.util.Random

@Composable
fun DottedProgressFillView(
    progress: Float,
    dotColor: Color = Color.Red,
    backgroundColor: Color = Color.Transparent,
    modifier: Modifier
) {
    val dotOpacity = 1f

    val numGridCells = (progress * 0.5).toInt()

    var width by remember { mutableIntStateOf(0) }
    var height by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .onGloballyPositioned {
                width = it.size.width
                height = it.size.height
            }
    ) {
        val random = Random()
        val filledGridCells = mutableSetOf<Pair<Int, Int>>()

        repeat(numGridCells) {
            var gridCellX: Int
            var gridCellY: Int

            do {
                gridCellX = random.nextInt(10)
                gridCellY = random.nextInt(5)
            } while (filledGridCells.contains(Pair(gridCellX, gridCellY)))

            Log.d("changes done : "," ${numGridCells} ${gridCellX} ${gridCellY}")

            filledGridCells.add(Pair(gridCellX, gridCellY))

            val cellWidth = (width / 10f + 1.dpToPx)
            val cellHeight = height / 5f

            val x = gridCellX * (width / 10f)
            val y = gridCellY * cellHeight

            Canvas(modifier = Modifier.offset(x.pxToDp.dp, y.pxToDp.dp)) {
                drawRect(
                    color = dotColor.copy(alpha = dotOpacity),
                    size = Size(width = cellWidth, height = cellHeight)
                )
            }
        }
    }
}

@Preview
@Composable
fun previewDottedProgress() {
    SimpleToDoAppTheme {
        DottedProgressFillView(
            progress = 50f,
            modifier = Modifier.fillMaxSize()
        )
    }
}