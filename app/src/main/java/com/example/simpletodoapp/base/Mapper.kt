package com.example.simpletodoapp.base

interface AppDataMapper<in I : AppData, out O : AppData> {

    fun map(input : I) :O
}