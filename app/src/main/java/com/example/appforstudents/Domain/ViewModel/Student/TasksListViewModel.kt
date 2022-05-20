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
import com.example.appforstudents.Presentation.Adapter.Student.TasksListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class TasksListViewModel(application: Application,val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData(Student())
    var tasksList = MutableLiveData<ArrayList<Task>>()
    private var dateSortList: ArrayList<String> = arrayListOf()
    var completedTasksList = MutableLiveData<ArrayList<CompletedTask>>()

    //Adapter
    var tasksListAdapter = MutableLiveData<GroupTasksListAdapter>()
    var startTaskListener = MutableLiveData<TasksListAdapter.StartTaskListener>()

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
        connector.readTasksListByCompletedTask({ setTasksListAdapter() }, tasksList, completedTasksList)
    }
    private fun setTasksListAdapter(){
        dateSortList.clear()
        for(task in tasksList.value!!){
            if (!dateSortList.contains(task.date)){
                dateSortList.add(task.date)
            }
        }
        tasksListAdapter.value = GroupTasksListAdapter(dateSortList, tasksList.value!!, mainModel, startTaskListener.value!!)
    }

    fun startTask(task: Task){
        if (task.typeTask == "Test"){
            val bundle = Bundle()
            bundle.putString("positionSolutionTestTask", task.id)

            replace("SolutionTestFragment", bundle)
        }
        else if(task.typeTask == "Answer"){
            val bundle = Bundle()
            bundle.putString("positionSolutionAnswerTask", task.id)

            replace("SolutionAnswerTaskFragment", bundle)
        }
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}