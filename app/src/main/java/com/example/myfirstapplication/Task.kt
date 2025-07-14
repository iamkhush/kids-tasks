package com.example.myfirstapplication

data class Task(
    val id: Int,
    val title: String,
    val imageResourceId: Int,
    var isCompleted: Boolean = false
) 