package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Presentation.Adapter.Student.CompletedTaskListAdapter
import com.example.appforstudents.Presentation.Adapter.Student.GroupCompletedTaskListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class CompletedTasksListViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData(Student())
    var completedTasksList = MutableLiveData<ArrayList<CompletedTask>>()
    private var dateSortList: ArrayList<String> = arrayListOf()

    //Adapter
    var completedTasksListAdapter = MutableLiveData<GroupCompletedTaskListAdapter>()

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

    //setRecyclerViewAdapter
    fun setCompletedTasksListAdapter(){
        dateSortList.clear()
        for(task in completedTasksList.value!!){
            if (!dateSortList.contains(task.date)){
                dateSortList.add(task.date)
            }
        }
        completedTasksListAdapter.value = GroupCompletedTaskListAdapter(dateSortList, completedTasksList.value!!, mainModel)
    }


    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}