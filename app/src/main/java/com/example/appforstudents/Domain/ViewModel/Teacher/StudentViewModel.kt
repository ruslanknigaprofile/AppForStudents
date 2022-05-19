package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.graphics.ColorSpace
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Repositories.ConectorDB

class StudentViewModel(application: Application, val mainModel: MainViewModelForTeacher) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData<Student>()

    //Repositories
    val connector = ConectorDB()


    //getModel
    fun getStudentById(id: String){
        connector.readStudentByID(id, student)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}