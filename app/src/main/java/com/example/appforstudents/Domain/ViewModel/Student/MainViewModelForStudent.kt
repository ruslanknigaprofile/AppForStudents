package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController

class MainViewModelForStudent(application: Application) : AndroidViewModel(application) {

    //Navigation
    private var navigation: NavigationClass? = null
    var currentFragment = MutableLiveData("GameTaskFragment")

    //Alert
    private var builderAlert = MutableLiveData<AlertDialog.Builder>()

    fun setNavigation(navController: NavController){
        navigation = NavigationClass(navController)
    }
    fun replace(course: String, bundle: Bundle?) {
        navigation?.replace(course, bundle)
        currentFragment.value = navigation?.from.toString()
    }


    //Alert
    fun setBuilder(builder: AlertDialog.Builder){
        builderAlert.value = builder
    }
    fun createSimpleDialog(title: String, message: String, function: () -> Unit){
        val builder = builderAlert.value
        builder?.setTitle(title)
        builder?.setMessage(message)

        builder?.setNegativeButton("Нет") { dialogInterface, i ->
        }
        builder?.setPositiveButton("Да") { dialogInterface, i ->
            function()
        }
        builder?.show()
    }

    fun createToast(text: String){
        Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show()
    }
}