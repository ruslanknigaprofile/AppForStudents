package com.example.appforstudents.Model

data class Student(
    var studentId: String = "",
    var name: String = "",
    var completedTask: ArrayList<CompletedTask> = arrayListOf(),
    var raiting: Int = 0,
    val completedTopic: ArrayList<CompletedTopic> = arrayListOf()
)