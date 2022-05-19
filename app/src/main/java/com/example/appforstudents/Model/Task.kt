package com.example.appforstudents.Model

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class Task(
    var id: String = "",
    var bodyTask: String = "",
    var typeTask: String = "",
    val listAnswers: ArrayList<String> = arrayListOf(),
    val checkBoolean: ArrayList<String> = arrayListOf(),
    val listImageUrl: ArrayList<String> = arrayListOf(),
    val date: String = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)).toString(),
    var time: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
    var teacherNames: String = "",
    var teacherId: String = "")