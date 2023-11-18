package com.example.simpletodoapp.data.dataStore.repoImpl

import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

class ToDoRepoImpl @Inject constructor(
    private val todoDataStore: DataStore<DailyTodos>
) : ToDoRepo {
    override suspend fun addDailyTodo(dailyTodos: ToDo): Boolean {
        todoDataStore.updateData {
            it.copy(
                data = it.data.mutate { list ->
                    list.add(dailyTodos)
                }
            )
        }

        return true
    }

//    override suspend fun getDailyTodo(): DailyTodos {
//        return todoDataStore.data.collectAsState(
//            initial = DailyTodos(data = persistentListOf())
//        ).value
//    }
}