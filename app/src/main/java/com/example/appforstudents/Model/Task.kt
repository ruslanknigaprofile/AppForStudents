package com.example.appforstudents.Model

import java.util.ArrayList

data class Task(
    var id: String = "",
    val bodyTask: String = "",
    val typeTask: String = "",
    val listAnswers: ArrayList<String> = arrayListOf(),
    val checkBoolean: ArrayList<String> = arrayListOf(),
    val listImageUrl: ArrayList<String> = arrayListOf())