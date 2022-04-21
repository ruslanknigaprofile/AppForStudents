package com.example.appforstudents.Model

data class CompletedTask(
    val task: ArrayList<Task> = arrayListOf(),
    val answer: ArrayList<String> = arrayListOf(),
    val asses: ArrayList<Boolean> = arrayListOf()
)