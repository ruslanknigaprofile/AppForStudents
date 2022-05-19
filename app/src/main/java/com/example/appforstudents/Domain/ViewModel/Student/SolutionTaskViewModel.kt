package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.Adapter.Student.GalleryAdapter
import com.example.appforstudents.Presentation.Adapter.Student.TestAdapter
import com.example.appforstudents.Repositories.ConectorDB

class SolutionTaskViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData(Student())
    var task = MutableLiveData<Task>()
    var taskId = MutableLiveData<String>()
    var completedTasksList = MutableLiveData<ArrayList<CompletedTask>>()
    var raiting = MutableLiveData<Int>()
    //var sliderImage = MutableLiveData<ArrayList<Uri>>(arrayListOf())

    //Adapter
    var testAdapter = MutableLiveData<TestAdapter>()
    var galleryAdapter = MutableLiveData<GalleryAdapter?>()

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
    fun getTask(){
        connector.readTask(taskId.value.toString(),task)
    }
    fun getData(){
        connector.readCompletedTasks(student.value!!.studentId, completedTasksList)
        connector.readRating(student.value!!.studentId, raiting)
    }
    /*
    fun getImagesForReview(){
        sliderImage.value?.clear()
        connector.getImages(task.value!!, { setSliderAdapter() }, sliderImage)
    }*/

    //setRecyclerViewAdapter
    /*private*/fun setSliderAdapter(){
        galleryAdapter.value = GalleryAdapter(/*sliderImage.value!!*/ task.value!!.listImageUrl, getApplication<Application>().baseContext, mainModel)
    }
    fun setTestAdapter(){
        testAdapter.value = TestAdapter(this)
    }

    //UpdateCompletedTask
    fun updateCompletedAnswerTask(solution: String){
        val completedTask = CompletedTask()
        completedTask.task = task.value!!
        completedTask.answer = solution
        if (solution == task.value!!.listAnswers.get(0)){
            completedTask.asses = true
            raiting.value = raiting.value?.plus(1)
        } else{
            completedTask.asses = false
            raiting.value = raiting.value?.minus(1)
        }

        completedTasksList.value?.add(0,completedTask)

        connector.updateStudentRaitingInDB(student.value!!.studentId, raiting.value!!)
        connector.updateStudentCompletedTaskInDB(student.value!!.studentId, completedTasksList.value!!)
        replace("CompletedTasksListFragment", null)
    }
    fun updateCompletedTestTask(solution: ArrayList<Boolean>){
        val completedTask = CompletedTask()
        completedTask.task = task.value!!

        var asses = true
        var answer = ""
        for ((index, i) in solution.withIndex()){
            if(i){
                answer += task.value!!.listAnswers.get(index) + ". "
            }
            if (i.toString() != task.value!!.checkBoolean.get(index)){
                asses = false
            }
        }

        completedTask.answer = answer

        if (asses){
            completedTask.asses = true
            raiting.value = raiting.value?.plus(1)
        } else{
            completedTask.asses = false
            raiting.value = raiting.value?.minus(1)
        }

        completedTasksList.value?.add(0,completedTask)

        connector.updateStudentRaitingInDB(student.value!!.studentId, raiting.value!!)
        connector.updateStudentCompletedTaskInDB(student.value!!.studentId, completedTasksList.value!!)
        replace("CompletedTasksListFragment", null)
    }
/*
    fun disposeGallery(){
        galleryAdapter.value = null
        sliderImage.value?.clear()
    }*/

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}