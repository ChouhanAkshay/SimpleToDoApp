package com.example.simpletodoapp.ui.viewmodels

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.usecase.AddNewTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addNewTodoUseCase: AddNewTodoUseCase
): ViewModel() {

    fun addNewTodoItem(isDailyTask : Boolean, title : String) {
        val todo = ToDo(
            isDailyTask = isDailyTask,
            title = title,
            isCompleted = false
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                addNewTodoUseCase.process(todo)
            }
        }
    }
}