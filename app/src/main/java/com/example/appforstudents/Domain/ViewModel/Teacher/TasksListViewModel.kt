package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Model.Teacher
import com.example.appforstudents.Presentation.Adapter.Teacher.GroupTasksListAdapter
import com.example.appforstudents.Presentation.Adapter.Teacher.TasksListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class TasksListViewModel(application: Application, val mainModel: MainViewModelForTeacher) : AndroidViewModel(application) {

    //Model
    var teacher = MutableLiveData(Teacher())
    var tasksList = MutableLiveData<ArrayList<Task>>()
    var dateSortList: ArrayList<String> = arrayListOf()
    var deleteTaskListener = MutableLiveData<TasksListAdapter.DeleteTaskListener>()

    //Adapter
    var tasksListAdapter = MutableLiveData<GroupTasksListAdapter>()

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
        dateSortList.clear()
        for(task in tasksList.value!!){
            if (!dateSortList.contains(task.date)){
                dateSortList.add(task.date)
            }
        }
        tasksListAdapter.value = GroupTasksListAdapter(dateSortList, tasksList.value!!, mainModel, deleteTaskListener.value!!)
    }
    fun deleteTask(id: String){
        connector.deleteTaskInDB(id)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}