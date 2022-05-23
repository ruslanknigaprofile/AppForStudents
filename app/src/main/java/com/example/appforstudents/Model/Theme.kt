package com.example.appforstudents.Model

data class Theme(
    val name: String = "",
    val symbol: String = "",
    val startNumber: Int = 0,
    val endNumber: Int = 0,
    val imageList: ArrayList<String> = arrayListOf()
)