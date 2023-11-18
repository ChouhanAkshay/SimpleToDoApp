package com.example.simpletodoapp.di.modules.serializables

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.simpletodoapp.data.dataStore.model.DailyTodos
import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import com.example.simpletodoapp.data.dataStore.repoImpl.ToDoRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    private val dailyTodoDataStoreName = "DailyTodoDataStore"

    private val Context.dailyTodoDataStore : DataStore<DailyTodos> by dataStore(
        fileName = dailyTodoDataStoreName,
        serializer = DailyToDoSerializer
    )

    @Provides
    @Reusable
    fun provideDailyTodoDataStore(@ApplicationContext context : Context) : DataStore<DailyTodos>{
        return context.dailyTodoDataStore
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    object DailyToDoSerializer : Serializer<DailyTodos> {
        override val defaultValue: DailyTodos
            get() = DailyTodos()

        override suspend fun readFrom(input: InputStream): DailyTodos {
            return try{
                Json.decodeFromString(
                    deserializer = DailyTodos.serializer(),
                    string = input.readBytes().decodeToString()
                )
            } catch (e : Exception) {
                e.printStackTrace()
                defaultValue
            }
        }

        override suspend fun writeTo(t: DailyTodos, output: OutputStream) {
            output.write(
                Json.encodeToString(
                    serializer = DailyTodos.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}
