package com.example.simpletodoapp.data.dataStore.usecase

import com.example.simpletodoapp.base.BaseUseCase
import com.example.simpletodoapp.base.BaseUseCaseWithInput
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import javax.inject.Inject

class ResetDailyTodoUseCase @Inject constructor(
    private val toDoRepo: ToDoRepo
) : BaseUseCase<Boolean>(){
    override suspend fun process(): Boolean {
        return toDoRepo.resetDailyTodo()
    }
}