package com.example.appforstudents.Presentation.View.Teacher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class MainActivityTeacher : AppCompatActivity() {

    private lateinit var vm: MainViewModelForTeacher
    private lateinit var botomBar: ChipNavigationBar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_for_teacher)

        navController = Navigation.findNavController(this, R.id.fragment_place)
        vm = ViewModelProvider(this).get(MainViewModelForTeacher::class.java)
        vm.setNavigation(navController)

        init()
    }

    private fun init(){
        botomBar = findViewById(R.id.bNav)
        botomBar.setItemSelected(R.id.TasksMenu)
        botomBar.setOnItemSelectedListener {
            when(it){
                R.id.TasksMenu ->{
                    vm.replace("TasksListFragment", null)
                }
                R.id.AddTask ->{
                    vm.replace("CreateTestFragment",null)
                }
                R.id.Students ->{
                    vm.replace("StudentsListFragment",null)
                }
            }
        }

        vm.currentFragment.observe(this){
            when(it){
                "TasksListFragment" ->{
                    botomBar.isVisible = true
                    botomBar.setItemSelected(R.id.TasksMenu)
                }
                "StudentsListFragment" ->{
                    botomBar.isVisible = true
                }
                "StudentFragment" ->{
                    botomBar.isVisible = false
                }
                "ReviewTestFragment" ->{
                    botomBar.isVisible = false
                }
                "ReviewAnswerFragment" ->{
                    botomBar.isVisible = false
                }
            }
        }
    }

    override fun onBackPressed() {
        when(vm.currentFragment.value){
            "CreateTestFragment" ->{
                vm.replace("TasksListFragment",null)
            }
            "CreateAnswerFragment" ->{
                vm.replace("TasksListFragment",null)
            }
            "GalleryFragment" ->{
                vm.replace("ReviewTestFragment", null)
                vm.replace("ReviewAnswerFragment", null)
            }
            "StudentFragment" ->{
                vm.replace("StudentsListFragment", null)
            }
            "ReviewTestFragment" ->{
                vm.replace("TasksListFragment", null)
            }
            "ReviewAnswerFragment" ->{
                vm.replace("TasksListFragment", null)
            }
        }
    }
}