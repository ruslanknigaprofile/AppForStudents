package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.appforstudents.Domain.ViewModel.Student.NavigationClass

class MainViewModelForTeacher(application: Application) : AndroidViewModel(application) {
    //Navigation
    private var navigation: NavigationClass? = null
    var currentFragment = MutableLiveData("TasksListFragment")


    fun setNavigation(navController: NavController){
        navigation = NavigationClass(navController)
    }
    fun replace(course: String, bundle: Bundle?) {
        navigation?.replace(course, bundle)
        currentFragment.value = navigation?.from.toString()
    }
}