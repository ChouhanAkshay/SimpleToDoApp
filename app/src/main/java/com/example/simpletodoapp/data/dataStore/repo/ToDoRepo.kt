package com.example.simpletodoapp.data.dataStore.repo

import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepo {
     suspend fun addDailyTodo(dailyTodos: ToDo) : Boolean
     suspend fun getDailyTodo() : Flow<DailyTodos>
     suspend fun completeToDo(dailyTodos: ToDo) : Boolean
     suspend fun deleteToDo(dailyTodos: ToDo) : Boolean
     suspend fun resetDailyTodo() : Boolean
}