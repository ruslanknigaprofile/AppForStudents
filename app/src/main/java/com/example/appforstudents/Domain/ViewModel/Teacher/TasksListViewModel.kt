package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Model.Teacher
import com.example.appforstudents.Presentation.Adapter.Teacher.TasksListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class TasksListViewModel(application: Application, val mainModel: MainViewModelForTeacher) : AndroidViewModel(application) {

    //Model
    var teacher = MutableLiveData(Teacher())
    var tasksList = MutableLiveData<ArrayList<Task>>()

    //Adapter
    var tasksListAdapter = MutableLiveData<TasksListAdapter>()

    //Repositories
    private val connector = ConectorDB()


    init {
        getSharedPreferences()
    }

    //setModel
    private fun getSharedPreferences(){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            teacher.value!!.teacherId = sharedPreferences.getString("id", null)!!
        }
    }

    //RecyclerViewAdapter
    fun getTasksList(){
        connector.readTasksListByTeacherId({ setTasksListAdapter() }, tasksList, teacher.value!!.teacherId)
    }
    private fun setTasksListAdapter(){
        tasksListAdapter.value = TasksListAdapter(this)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}