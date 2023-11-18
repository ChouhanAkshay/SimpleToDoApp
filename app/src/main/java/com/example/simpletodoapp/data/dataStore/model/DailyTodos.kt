package com.example.simpletodoapp.data.dataStore.model

import com.example.simpletodoapp.base.AppData
import com.example.simpletodoapp.di.modules.serializables.ToDoPersistantListSerializer
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class DailyTodos(
    @Serializable(with = ToDoPersistantListSerializer::class)
    val data : PersistentList<ToDo> = persistentListOf()
) : AppData

@Serializable
data class ToDo(
    val title : String,
    val isDailyTask : Boolean,
    val isCompleted : Boolean
) : AppData