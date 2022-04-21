package com.example.appforstudents.Domain.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Student
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    var student = MutableLiveData(Student())
    private val db = FirebaseDatabase.getInstance().getReference("StudentsID")


    private fun writeInDB(string: String){
        val push = db.push()
        student.value!!.name = string
        student.value!!.studentId = push.key.toString()
        push.setValue(student.value)
    }

    fun createId(names: String){
        writeInDB(names)

        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit().apply {
            putString("id", student.value?.studentId)
        }.apply()
    }

    fun loadId(function: () -> Unit){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            function()
        }
    }
}