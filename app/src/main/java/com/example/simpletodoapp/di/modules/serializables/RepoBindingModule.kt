package com.example.simpletodoapp.di.modules.serializables

import com.example.simpletodoapp.data.dataStore.repo.ToDoRepo
import com.example.simpletodoapp.data.dataStore.repoImpl.ToDoRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoBindingModule {

    @Binds
    abstract fun provideTodoRepoImpl( repo : ToDoRepoImpl) : ToDoRepo
}