package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Model.Topic
import com.example.appforstudents.Presentation.Adapter.Student.GameAdapter
import com.example.appforstudents.Presentation.Adapter.Student.GroupTasksListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class GameTaskViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    private var studentId: String = ""
    var student = MutableLiveData<Student>()
    var topicList = MutableLiveData<ArrayList<Topic>>()
    var completedTasksList = MutableLiveData<ArrayList<CompletedTask>>()

    //Adapter
    var tasksListAdapter = MutableLiveData<GameAdapter>()

    //Repositories
    private val connector = ConectorDB()

    init {
        getSharedPreferences()
    }

    //setModel
    private fun getSharedPreferences(){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            studentId = sharedPreferences.getString("id", null)!!
        }
    }
    fun getData(){
        connector.readStudentByID(studentId, student)
        connector.getTopic(topicList)
    }

    //setAdapter
    fun setGameTaskAdapter(){
        tasksListAdapter.value = GameAdapter(student.value!!, topicList.value!!, mainModel)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}