package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController

class MainViewModelForStudent(application: Application) : AndroidViewModel(application) {

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