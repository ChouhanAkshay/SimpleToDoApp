package com.example.simpletodoapp.data.dataStore.model

import com.example.simpletodoapp.base.AppData
import kotlinx.serialization.Serializable

@Serializable
data class TimestrampData(
    var updatedAt : String? = null
) : AppData