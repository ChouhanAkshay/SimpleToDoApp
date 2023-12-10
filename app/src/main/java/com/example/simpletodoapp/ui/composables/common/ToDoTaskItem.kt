package com.example.simpletodoapp.ui.composables.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.simpletodoapp.R
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.ui.theme.SimpleToDoAppTheme
import com.example.simpletodoapp.utils.extension_functions.dpToPx
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTaskItem(
    data: ToDo, onFinished: () -> Unit, onDelete: () -> Unit
) {
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(data)
    val dismissState = rememberDismissState(confirmValueChange = {
        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
            show = false
            true
        } else
            false
    }, positionalThreshold = { 150.dpToPx.toFloat() }
    )

    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier,
            backgroundContent = {
                SwipeToDeleteBackground(dismissState)
            },
            content = {
                ElevatedCard(
                    onClick = {},
                    enabled = !currentItem.isCompleted,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        val (content, background) = createRefs()

                        DottedProgressFillView(
                            currentItem.monthlyCompletionProgress.toFloat(),
                            dotColor = Color.Yellow,
                            modifier = Modifier.constrainAs(background) {
                                top.linkTo(content.top)
                                bottom.linkTo(content.bottom)
                                start.linkTo(content.start)
                                end.linkTo(content.end)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                        )

                        Row(
                            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp)
                                .constrainAs(content) {
                                }) {
                            Column(modifier = Modifier.weight(1f)) {
                                //text -> Monthly progress : 0 %
                                Text(
                                    text = "${stringResource(R.string.monthly_progress)} ${currentItem.monthlyCompletionProgress} ${
                                        stringResource(
                                            R.string.percent
                                        )
                                    }",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.DarkGray
                                )

                                Row {
                                    Text(
                                        text = currentItem.title,
                                        modifier = Modifier.weight(1f).padding(top = 4.dp),
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                }
                            }

                            if (!data.isCompleted) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_circle_20),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(45.dp)
                                        .align(Alignment.CenterVertically)
                                        .clickable {
                                            onFinished()
                                        }
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(800)
            onDelete()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    shape = RoundedCornerShape(50.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (direction == DismissDirection.StartToEnd) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    text = stringResource(R.string.delete).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier)

            if (direction == DismissDirection.EndToStart) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    text = stringResource(R.string.delete).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun previewTodoTaskItem() {
    SimpleToDoAppTheme {
        ToDoTaskItem(ToDo(
            title = "abcd jsfksfsfs kljfj ksjk",
            isCompleted = false,
            isDailyTask = true,
            monthlyCompletionProgress = 50
        ), {
        }, {
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun previewDeleteBackground() {
    SimpleToDoAppTheme {
        SwipeToDeleteBackground(
            dismissState = DismissState(
                initialValue = DismissValue.Default,
                density = Density(density = 1f)
            ) {
                1f
            })
    }
}