package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.widget.AdapterView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Presentation.Adapter.Teacher.StudentsListAdapter
import com.example.appforstudents.Repositories.ConectorDB

class StudentsListViewModel (application: Application, val mainModel: MainViewModelForTeacher) : AndroidViewModel(application) {

    //Model
    var studentsList = MutableLiveData<ArrayList<Student>>(arrayListOf())

    //Adapters
    var studentsListAdapter = MutableLiveData<StudentsListAdapter>()

    //Repositories
    private val connector = ConectorDB()

    //getModel
    fun getStudents(){
        connector.readStudentsLists(studentsList, { setStudentsListAdapter() })
    }

    //setAdapters
    private fun setStudentsListAdapter(){
        studentsListAdapter.value = StudentsListAdapter(studentsList.value!!, mainModel)
    }
}