package com.example.simpletodoapp.base

abstract class BaseUseCaseWithInput<in I : AppData, out O> {
    abstract suspend fun process(input : I) : O
}

abstract class BaseUserCase<out O> {
    abstract suspend fun process() : O
}