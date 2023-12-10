package com.example.simpletodoapp.data.dataStore.repoImpl

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.TimestrampData
import com.example.simpletodoapp.data.dataStore.model.ToDo
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import com.example.simpletodoapp.utils.DateUtils
import com.example.simpletodoapp.utils.helper.GeneralHelper
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/***
 * Storing timestramp in datastore because of small data and less updates on it
 */

class ToDoRepoImpl @Inject constructor(
    private val todoDataStore: DataStore<DailyTodos>,
    private val timeStrampDataStore: DataStore<TimestrampData>
) : ToDoRepo {
    override suspend fun addDailyTodo(dailyTodos: ToDo): Boolean {
        todoDataStore.updateData {
            it.copy(
                data = it.data.mutate { list ->
                    val deletedItem = list.find { it.isDeleted }

                    deletedItem?.let { item ->
                        list.remove(item)
                    }

                    list.add(dailyTodos)
                }
            )
        }

        return true
    }

    override suspend fun getDailyTodo(): Flow<DailyTodos> {
        return todoDataStore.data
    }

    override suspend fun completeToDo(dailyTodos: ToDo): Boolean {
        val days = dailyTodos.numberOfDaysTaskCompleted + 1

        todoDataStore.updateData {
            it.copy(
                data = it.data.mutate { list ->
                    list[list.indexOf(list.find { it.id == dailyTodos.id })] = ToDo(
                        title = dailyTodos.title,
                        isCompleted = true,
                        isDeleted = dailyTodos.isDeleted,
                        isDailyTask = dailyTodos.isDailyTask,
                        id = dailyTodos.id,
                        numberOfDaysTaskCompleted = days,
                        monthlyCompletionProgress = GeneralHelper.getMonthlyTaskCompletionProgress(
                            days
                        )
                    )
                }
            )
        }
        return true
    }

    override suspend fun deleteToDo(dailyTodos: ToDo): Boolean {
        todoDataStore.updateData {
            it.copy(
                data = it.data.mutate { list ->
                    list[list.indexOf(list.find { it.id == dailyTodos.id })] = ToDo(
                        title = dailyTodos.title,
                        isCompleted = true,
                        isDeleted = true,
                        isDailyTask = dailyTodos.isDailyTask,
                        id = dailyTodos.id
                    )
                }
            )
        }
        return true
    }

    override suspend fun resetDailyTodo(): Boolean {
        timeStrampDataStore.data.first().let {
            val date = DateUtils.getTodayDate()
            if (it.updatedAt.equals(date)) {
                timeStrampDataStore.updateData {
                    todoDataStore.updateData {
                        Log.d("data ", "persisted work")

                        it.copy(
                            data = it.data.mutate { list ->
                                val tempList = mutableListOf<ToDo>()

                                list.forEachIndexed { index, toDo ->
                                    val item = ToDo(
                                        id = toDo.id,
                                        title = toDo.title,
                                        isCompleted = false,
                                        isDeleted = toDo.isDeleted,
                                        isDailyTask = toDo.isDailyTask,
                                        monthlyCompletionProgress = toDo.monthlyCompletionProgress,
                                        numberOfDaysTaskCompleted = toDo.numberOfDaysTaskCompleted
                                    )

                                    tempList.add(item)
                                }

                                tempList.forEachIndexed { index, toDo ->
                                    list[index] = tempList[index]
                                }
                            }
                        )
                    }
                    Log.d("data ", "persisted work1")
                    it.copy(
                        updatedAt = date
                    )
                }
            }
        }

        return true
    }
}