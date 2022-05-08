package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Presentation.Adapter.GalleryAdapter
import com.example.appforstudents.Repositories.ConectorDB

class ReviewCompletedTaskViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    var student = MutableLiveData(Student())
    var completedTasksList = MutableLiveData<ArrayList<CompletedTask>>()
    var galleryAdapter = MutableLiveData<GalleryAdapter?>()
    var sliderImage = MutableLiveData<ArrayList<Uri>>(arrayListOf())
    var position = MutableLiveData<Int>()

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
    fun getImagesForReview(){
        sliderImage.value?.clear()
        connector.getImages(completedTasksList.value!!.get(position.value!!).task, { setSliderAdapter() }, sliderImage)
    }

    //Adapter
    private fun setSliderAdapter(){
        galleryAdapter.value = GalleryAdapter(sliderImage.value!!, getApplication<Application>().baseContext, mainModel)
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course,bundle)
    }
}