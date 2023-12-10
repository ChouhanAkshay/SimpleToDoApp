package com.example.simpletodoapp.data.dataStore.usecase

import com.example.simpletodoapp.base.BaseUseCaseWithInput
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val toDoRepo: ToDoRepo
) : BaseUseCaseWithInput<ToDo, Boolean>(){
    override suspend fun process(input: ToDo): Boolean {
        return toDoRepo.deleteToDo(input)
    }
}