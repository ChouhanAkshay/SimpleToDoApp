package com.example.simpletodoapp.ui.composables.screens

import android.util.Log
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletodoapp.R
import com.example.simpletodoapp.ui.composables.common.ToDoTaskItem
import com.example.simpletodoapp.ui.theme.SimpleToDoAppTheme
import com.example.simpletodoapp.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: MainViewModel, onAddNewTask: (isDailyTask: Boolean, title: String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    var refreshCount by remember { mutableIntStateOf(1) }
    val dailyPendingTodo = vm.dailyTodos.observeAsState()
    val listOfDailyTodo by vm._listOfDailyTodo
    val listState = rememberLazyListState()

    //get Data
    LaunchedEffect(key1 = refreshCount) {
        vm.getDailyPendingToDos()
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(R.string.lets_do_it),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }, actions = {
            IconButton(onClick = {
                coroutineScope.launch {
                    isSheetOpen.value = true
                }
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.width(45.dp).height(45.dp).padding(5.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            //show daily progress
            DailyTodoCompletionProgress(dailyPendingTodo?.value?.percentageCompleted)
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOfDailyTodo?.size ?: 0) { index ->
                    listOfDailyTodo?.get(index)?.let {
                        if (!it.isDeleted) {
                            key(it.id) {
                                ToDoTaskItem(
                                    it,
                                    {
                                        vm.completeTask(it)
                                    }, {
                                        vm.deleteTodo(it)
                                    })
                            }
                        }
                    }
                }
            }
        }
        AddNewTaskBottomSheet(isSheetOpen) { isDailyTask, title ->
            onAddNewTask(isDailyTask, title)
        }
    }
}

@Composable
fun DailyTodoCompletionProgress(progress: Float?) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(R.string.daily_progress),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
        )

        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Card(
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.height(18.dp).weight(1f)
            ) {
                LinearProgressIndicator(
                    progress = { (progress ?: 0f) / 100 },
                    modifier = Modifier.height(18.dp).fillMaxWidth(),
                    color = Color.Green
                )
            }

            Text(
                modifier = Modifier.wrapContentSize().padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                text = "${progress?.toInt()} ${stringResource(R.string.percent)}",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Green,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun previewHomeScreen() {
    SimpleToDoAppTheme {
        HomeScreen(hiltViewModel(), { _, _ ->

        })
    }
}

@Preview
@Composable
fun DailyTodoCompletionProgressPreview() {
    SimpleToDoAppTheme {
        DailyTodoCompletionProgress(50f)
    }
}