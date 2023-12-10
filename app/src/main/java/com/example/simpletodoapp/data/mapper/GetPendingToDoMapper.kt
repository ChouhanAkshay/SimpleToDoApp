package com.example.simpletodoapp.data.mapper

import com.example.simpletodoapp.base.AppDataMapper
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.model.ToDo
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

class GetPendingToDoMapper @Inject constructor() : AppDataMapper<DailyTodos, DailyTodos> {
    override fun map(input: DailyTodos): DailyTodos {

        var pendingTodos = 0
        var total = 0

        val listOfTodo: MutableList<ToDo> = mutableListOf()
            input.data.forEach { item ->

                if(!item.isCompleted && !item.isDeleted) {
                    pendingTodos++
                }

                if(!item.isDeleted) {
                    total ++
                }

                val copy = item.copy(
                    id = item.id,
                    title = item.title,
                    isCompleted = item.isCompleted,
                    isDailyTask = item.isDailyTask,
                    isDeleted = item.isDeleted,
                    numberOfDaysTaskCompleted = item.numberOfDaysTaskCompleted,
                    monthlyCompletionProgress = item.monthlyCompletionProgress
                )

                listOfTodo.add(copy)
            }

        val finished = total - pendingTodos

        val result = DailyTodos(
            data = listOfTodo.toPersistentList(),
            total = total,
            pending = pendingTodos,
            percentageCompleted = (finished.toFloat() / total) * 100,
            finished = finished,
        )

        return result
    }
}