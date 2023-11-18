package com.example.simpletodoapp.ui.composables.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.R
import com.example.simpletodoapp.ui.theme.SimpleToDoAppTheme
import com.example.simpletodoapp.utils.AppConstants.emptyString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskBottomSheet(
    isSheetOpen: MutableState<Boolean>,
    onDone: (isDailyTask: Boolean, title: String) -> Unit
) {
    val bottomSheetState: SheetState = rememberModalBottomSheetState()

    if (isSheetOpen.value) {
        ModalBottomSheet(
            onDismissRequest = {
                isSheetOpen.value = false
            },
            sheetState = bottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(
                bottom = 35.dp
            ).clickable {
                //do nothing
            },
            containerColor = Color.White
        ) {
            val todoTitle = remember { mutableStateOf(emptyString) }
            val isCheckboxChecked = remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 16.dp)
                    .padding(
                        bottom = 20.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(
                        paddingValues = PaddingValues(vertical = 8.dp)
                    ).clickable(onClick = {
                        isCheckboxChecked.value = !isCheckboxChecked.value
                    })
                ) {
                    Checkbox(
                        checked = isCheckboxChecked.value, onCheckedChange = null,
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Text(
                        text = stringResource(R.string.add_in_daily_tasks),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f).align(
                            alignment = Alignment.CenterVertically
                        ).padding(horizontal = 8.dp)
                    )
                    Button(
                        onClick = {
                            isSheetOpen.value = false
                            onDone(isCheckboxChecked.value, todoTitle.value)
                        }, shape = RoundedCornerShape(20.dp), modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        ).wrapContentWidth(align = Alignment.End).wrapContentHeight()
                    ) {
                        Text(
                            text = stringResource(R.string.done)
                        )
                    }
                }

                OutlinedTextField(value = todoTitle.value, onValueChange = { textValue: String ->
                    todoTitle.value = textValue
                }, modifier = Modifier.fillMaxWidth().wrapContentHeight(), label = {
                    Text(
                        text = stringResource(R.string.enter_todo)
                    )
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ComposableNaming")
@Preview()
@Composable
fun reviewBottomSheet() {
    SimpleToDoAppTheme {
        AddNewTaskBottomSheet(
            rememberSaveable { mutableStateOf(true) },
            onDone = { a, b ->

            }
        )
    }
}