package com.example.appforstudents.Model

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class CompletedTask(
    var task: Task = Task(),
    var answer: String = "",
    var asses: Boolean = false,
    val date: String = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)).toString(),
    var time: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString()
)