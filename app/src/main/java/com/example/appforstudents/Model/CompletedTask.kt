package com.example.appforstudents.Model

data class CompletedTask(
    var task: Task = Task(),
    var answer: String = "",
    var asses: Boolean = false
)