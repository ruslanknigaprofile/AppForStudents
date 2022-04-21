package com.example.appforstudents.Model

data class Student(
    var studentId: String = "",
    var name: String = "",
    var completedTask: CompletedTask = CompletedTask(),
    var raiting: Int = 0
)