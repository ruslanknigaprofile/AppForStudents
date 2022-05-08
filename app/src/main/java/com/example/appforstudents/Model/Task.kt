package com.example.appforstudents.Model

import java.time.LocalDate
import java.util.*

data class Task(
    var id: String = "",
    val bodyTask: String = "",
    val typeTask: String = "",
    val listAnswers: ArrayList<String> = arrayListOf(),
    val checkBoolean: ArrayList<String> = arrayListOf(),
    val listImageUrl: ArrayList<String> = arrayListOf(),
    val date: LocalDate = LocalDate.now(),
    val teacherNames: String = "",
    val teacherId: String = "")