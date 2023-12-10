package com.example.simpletodoapp.data.dataStore.usecase

import androidx.compose.runtime.collectAsState
import com.example.simpletodoapp.base.BaseUseCase
import com.example.simpletodoapp.base.BaseUseCaseWithInput
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import com.example.simpletodoapp.data.mapper.GetPendingToDoMapper
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@ViewModelScoped
class AddNewTodoUseCase @Inject constructor(
    private val toDoRepo: ToDoRepo
) : BaseUseCaseWithInput<ToDo, Boolean>(){
    override suspend fun process(input: ToDo): Boolean {
        return toDoRepo.addDailyTodo(input)
    }
}

class GetDailyPendingTodosUseCase @Inject constructor(
    private val toDoRepo: ToDoRepo,
    private val getPendingToDoMapper: GetPendingToDoMapper
) : BaseUseCase<DailyTodos>(){
    override suspend fun process(): DailyTodos {
        val result = toDoRepo.getDailyTodo().first()
        return getPendingToDoMapper.map(result)
    }
}