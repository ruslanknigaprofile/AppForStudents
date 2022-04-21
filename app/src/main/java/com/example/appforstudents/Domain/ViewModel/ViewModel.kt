package com.example.appforstudents.Domain.ViewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.Adapter.CompletedTaskListAdapter
import com.example.appforstudents.Presentation.Adapter.GalleryAdapter
import com.example.appforstudents.Presentation.Adapter.TasksListAdapter
import com.example.appforstudents.Presentation.Adapter.TestAdapter
import com.example.appforstudents.Repositories.ConectorDB
import kotlinx.coroutines.*

class ViewModel(application: Application) : AndroidViewModel(application) {

    //Navigation
    private var navigation: NavigationClass? = null
    var currentFragment = MutableLiveData<String>("TasksListFragment")
    var possitionSolutionTask = MutableLiveData<Int>()
    var possitionGalleryItem = MutableLiveData<Int>()
    var possitionReviewCompletedTask = MutableLiveData<Int>()

    //Model
    var student = MutableLiveData(Student())
    var tasksList = MutableLiveData<ArrayList<Task>>(arrayListOf())
    var completedTasksList = MutableLiveData(CompletedTask())

    //RecyclerViewAdapter
    var tasksListAdapter = MutableLiveData<TasksListAdapter>()
    var completedTasksListAdapter = MutableLiveData<CompletedTaskListAdapter>()
    var galleryAdapter = MutableLiveData<GalleryAdapter?>()
    var sliderImage = MutableLiveData<ArrayList<Uri>>(arrayListOf())
    var testAdapter = MutableLiveData<TestAdapter>()

    //Repositories
    private val connector = ConectorDB()

    init {
        //setModel
        getSharedPreferences()
        connector.readStudentID(this)
    }

    //getModel
    private fun getSharedPreferences(){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            student.value!!.studentId = sharedPreferences.getString("id", null)!!
        }
    }

    //setRecyclerViewAdapter
    fun getTasksList(){
        connector.readTasksList({ setTasksListAdapter() }, this)
    }
    private fun setTasksListAdapter(){
        tasksListAdapter.value = TasksListAdapter(this)
    }
    fun setCompletedTasksListAdapter(){
        completedTasksListAdapter.value = CompletedTaskListAdapter(this)
    }
    fun setTestAdapter(){
        testAdapter.value = TestAdapter(this)
    }

    //UpdateCompletedTask
    fun updateCompletedAnswerTask(solution: String){
        completedTasksList.value = student.value?.completedTask

        var rating = 0
        completedTasksList.value?.task?.add(0, tasksList.value?.get(possitionSolutionTask.value!!)!!)
        completedTasksList.value?.answer?.add(0, solution)
        if (solution == tasksList.value?.get(possitionSolutionTask.value!!)!!.listAnswers.get(0)){
            completedTasksList.value?.asses?.add(0, true)
            rating += 1
        } else{
            completedTasksList.value?.asses?.add(0,false)
        }

        student.value?.completedTask = completedTasksList.value!!
        student.value?.raiting = student.value?.raiting?.plus(rating)!!

        connector.updateStudentCompletedTaskInDB(student.value!!)
        replace("CompletedTasksListFragment")
    }
    fun updateCompletedTestTask(solution: ArrayList<Boolean>){
        completedTasksList.value = student.value?.completedTask

        var rating = 0
        completedTasksList.value?.task?.add(0, tasksList.value?.get(possitionSolutionTask.value!!)!!)

        var asses = true
        var answer = ""
        for ((index, i) in solution.withIndex()){
            if(i){
                answer += tasksList.value?.get(possitionSolutionTask.value!!)!!.listAnswers.get(index) + ". "
            }
            if (i.toString() != tasksList.value?.get(possitionSolutionTask.value!!)!!.checkBoolean.get(index)){
                asses = false
            }
        }

        completedTasksList.value?.answer?.add(0, answer)

        if (asses){
            completedTasksList.value?.asses?.add(0, true)
            rating += 1
        } else{
            completedTasksList.value?.asses?.add(0,false)
        }

        student.value?.completedTask = completedTasksList.value!!
        student.value?.raiting = student.value?.raiting?.plus(rating)!!

        connector.updateStudentCompletedTaskInDB(student.value!!)
        replace("CompletedTasksListFragment")
    }

    //SolutionTasks
    private fun setSliderAdapter(){
        galleryAdapter.value = GalleryAdapter(this)
    }
    fun getImagesForSolution(){
        sliderImage.value?.clear()
        connector.getImages(tasksList.value!!.get(possitionSolutionTask.value!!), { setSliderAdapter() }, this)
    }
    fun getImagesForReview(){
        sliderImage.value?.clear()
        connector.getImages(student.value!!.completedTask.task.get(possitionReviewCompletedTask.value!!), { setSliderAdapter() }, this)
    }

    //Dispose
    fun disposeSolutionTask(){
        galleryAdapter.value = null
    }

    //Navigation
    fun setNavigation(navController: NavController){
        navigation = NavigationClass(navController)
    }
    fun replace(course: String) {
        navigation?.replace(course)
        currentFragment.value = navigation?.from.toString()
    }
}