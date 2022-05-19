package com.example.appforstudents.Model

import java.time.LocalDate

data class CompletedTask(
    var task: Task = Task(),
    var answer: String = "",
    var asses: Boolean = false,
    val date: String = LocalDate.now().toString()
)