package com.example.appforstudents.Domain.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Teacher
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    var student = MutableLiveData(Student())
    var teacher = MutableLiveData(Teacher())
    var haveData = MutableLiveData(true)
    var position = MutableLiveData("")
    private val db = FirebaseDatabase.getInstance()


    private fun writeStudentInDB(string: String){
        val push = db.getReference("StudentsID").push()
        student.value!!.name = string
        student.value!!.studentId = push.key.toString()
        push.setValue(student.value)
    }

    private fun writeTeacherInDB(string: String){
        val push = db.getReference("TeachersID").push()
        teacher.value!!.name = string
        teacher.value!!.teacherId = push.key.toString()
        push.setValue(teacher.value)
    }

    fun createId(names: String, position: String){
        if (position == "Student"){
            writeStudentInDB(names)
        } else{
            writeTeacherInDB(names)
        }
        this.position.value = position


        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit().apply {
            putString("position", position)
            if (position == "Student"){
                putString("id", student.value?.studentId)
            }else{
                putString("id", teacher.value?.teacherId)
            }

        }.apply()
    }

    fun loadId(function: () -> Unit){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            position.value = sharedPreferences.getString("position", null)
            function()
        } else{
            haveData.value = false
        }
    }
}