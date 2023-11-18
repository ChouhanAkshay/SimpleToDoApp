package com.example.simpletodoapp.data.dataStore.repo

import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import javax.inject.Inject

interface ToDoRepo {
     suspend fun addDailyTodo(dailyTodos: ToDo) : Boolean
//     suspend fun getDailyTodo() : DailyTodos
}