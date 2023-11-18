package com.example.simpletodoapp.data.dataStore.usecase

import com.example.simpletodoapp.base.BaseUseCaseWithInput
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddNewTodoUseCase @Inject constructor(
    private val toDoRepo: ToDoRepo
) : BaseUseCaseWithInput<ToDo, Boolean>(){
    override suspend fun process(input: ToDo): Boolean {
        return toDoRepo.addDailyTodo(input)
    }
}