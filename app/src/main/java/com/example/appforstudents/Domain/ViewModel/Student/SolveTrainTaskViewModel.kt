package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.Adapter.Student.GroupTasksListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class SolveTrainTaskViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData(Student())


    //Adapter


    //Repositories
    private val connector = ConectorDB()


    init {
        getSharedPreferences()
    }

    //setModel
    private fun getSharedPreferences(){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            student.value!!.studentId = sharedPreferences.getString("id", null)!!
        }
    }


    //RecyclerViewAdapter


    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}