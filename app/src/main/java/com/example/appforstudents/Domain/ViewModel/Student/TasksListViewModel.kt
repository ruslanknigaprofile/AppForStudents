package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.Adapter.TasksListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class TasksListViewModel(application: Application,val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData(Student())
    var tasksList = MutableLiveData<ArrayList<Task>>()
    var completedTasksList = MutableLiveData<ArrayList<CompletedTask>>()

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
            student.value!!.studentId = sharedPreferences.getString("id", null)!!
        }
    }
    fun getCompletedTasks(){
        connector.readCompletedTasks(student.value!!.studentId, completedTasksList)
    }

    //RecyclerViewAdapter
    fun getTasksList(){
        connector.readTasksList({ setTasksListAdapter() }, tasksList, completedTasksList)
    }
    private fun setTasksListAdapter(){
        tasksListAdapter.value = TasksListAdapter(this)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}