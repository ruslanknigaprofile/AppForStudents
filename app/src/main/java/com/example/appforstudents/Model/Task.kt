package com.example.appforstudents.Model

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class Task(
    var id: String = "",
    var bodyTask: String = "",
    var typeTask: String = "",
    val listAnswers: ArrayList<String> = arrayListOf(),
    val checkBoolean: ArrayList<String> = arrayListOf(),
    val listImageUrl: ArrayList<String> = arrayListOf(),
    val date: String = LocalDate.now().toString(),
    var time: String = LocalTime.now().toString(),
    var teacherNames: String = "",
    var teacherId: String = "")