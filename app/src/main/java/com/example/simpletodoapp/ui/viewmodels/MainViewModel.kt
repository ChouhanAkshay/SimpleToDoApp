package com.example.simpletodoapp.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.usecase.AddNewTodoUseCase
import com.example.simpletodoapp.data.dataStore.usecase.CompleteTodoUseCase
import com.example.simpletodoapp.data.dataStore.usecase.DeleteTodoUseCase
import com.example.simpletodoapp.data.dataStore.usecase.GetDailyPendingTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addNewTodoUseCase: AddNewTodoUseCase,
    private val getDailyPendingTodosUseCase: GetDailyPendingTodosUseCase,
    private val completeTodoUseCase: CompleteTodoUseCase,
    private val deleteTodoUseCase : DeleteTodoUseCase,
): ViewModel() {

    val dailyTodos = MutableLiveData(DailyTodos())
    private val listOfDailyTodo = mutableStateOf<List<ToDo>>(listOf())
    val _listOfDailyTodo : State<List<ToDo>> = listOfDailyTodo


    fun addNewTodoItem(isDailyTask : Boolean, title : String) {
        val todo = ToDo(
            isDailyTask = isDailyTask,
            title = title,
            isCompleted = false,
            isDeleted = false,
            numberOfDaysTaskCompleted = 0,
            monthlyCompletionProgress = 0,
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                if(addNewTodoUseCase.process(todo)) {
                    getDailyPendingToDos()
                }
            }
        }
    }

    fun getDailyPendingToDos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = getDailyPendingTodosUseCase.process()
                dailyTodos.postValue(data)
                listOfDailyTodo.value  = data.data.toList()
            }
        }
    }

    fun completeTask(item : ToDo?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                if(completeTodoUseCase.process(item ?: ToDo())) {
                    getDailyPendingToDos()
                }
            }
        }
    }

    fun deleteTodo(item : ToDo?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                if(deleteTodoUseCase.process(item ?: ToDo())) {
                    getDailyPendingToDos()
                }
            }
        }
    }
}