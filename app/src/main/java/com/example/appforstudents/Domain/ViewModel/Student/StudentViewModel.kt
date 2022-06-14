package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Repositories.ConectorDB

class StudentViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData<Student>()

    //Repositories
    val connector = ConectorDB()


    //getModel
    fun getStudentById(){
        var id = ""
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null) {
            id = sharedPreferences.getString("id", null)!!
        }
        connector.readStudentByID(id, student)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}