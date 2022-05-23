package com.example.appforstudents.Model

data class CompletedTopic(
    val topic: Topic = Topic(),
    val themeStar: ArrayList<Int> = arrayListOf()
)