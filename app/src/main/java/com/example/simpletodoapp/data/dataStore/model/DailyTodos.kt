package com.example.simpletodoapp.data.dataStore.model

import com.example.simpletodoapp.base.AppData
import com.example.simpletodoapp.di.modules.serializables.ToDoPersistantListSerializer
import com.example.simpletodoapp.utils.AppConstants.emptyString
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class DailyTodos(
    @Serializable(with = ToDoPersistantListSerializer::class)
    val data : PersistentList<ToDo> = persistentListOf(),
    val total : Int = 0,
    val finished : Int = 0,
    val pending : Int = 0,
    val percentageCompleted : Float = 0f
) : AppData

@Serializable
data class ToDo(
    val id : String = UUID.randomUUID().toString(),
    val title : String = emptyString,
    val isDailyTask : Boolean = false,
    val isCompleted : Boolean = false,
    val isDeleted : Boolean = false,
    val monthlyCompletionProgress : Int = 0,
    val numberOfDaysTaskCompleted : Int = 0
) : AppData