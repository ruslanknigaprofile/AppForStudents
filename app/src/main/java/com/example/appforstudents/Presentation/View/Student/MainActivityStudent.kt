package com.example.appforstudents.Presentation.View.Student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appforstudents.R
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivityStudent : AppCompatActivity() {

    private lateinit var vm: MainViewModelForStudent

    private lateinit var botomBar: ChipNavigationBar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_for_student)
        vm = ViewModelProvider(this).get(MainViewModelForStudent::class.java)
        navController = Navigation.findNavController(this, R.id.fragment_place)
        vm.setNavigation(navController)

        init()
    }

    private fun init(){
        botomBar = findViewById(R.id.bNav)
        botomBar.setItemSelected(R.id.TasksMenu)
        botomBar.setOnItemSelectedListener {
            when(it){
                R.id.TasksMenu ->{
                    vm.replace("TasksListFragment",null)
                }
                R.id.CompletedTasksMenu ->{
                    vm.replace("CompletedTasksListFragment",null)
                }
            }
        }

        vm.currentFragment.observe(this){
            when(it){
                "TasksListFragment" ->{
                    botomBar.isVisible = true
                }
                "CompletedTasksListFragment" ->{
                    botomBar.setItemSelected(R.id.CompletedTasksMenu)
                    botomBar.isVisible = true
                }
                "SolutionAnswerTaskFragment" ->{
                    botomBar.isVisible = false
                }
                "SolutionTestFragment" ->{
                    botomBar.isVisible = false
                }
                "ReviewCompletedTaskFragment" ->{
                    botomBar.isVisible = false
                }
            }
        }
    }

    override fun onBackPressed() {
        when(vm.currentFragment.value){
            "TasksListFragment" ->{
                //1
            }
            "CompletedTasksListFragment" ->{
                //2
            }
            "SolutionAnswerTaskFragment" ->{
                vm.createToast("Завершите задание!")
            }
            "SolutionTestFragment" ->{
                vm.createToast("Завершите задание!")
            }
            "GalleryFragment" ->{
                vm.replace("SolutionAnswerTaskFragment",null)
                vm.replace("SolutionTestFragment",null)
                vm.replace("ReviewCompletedTaskFragment",null)
            }
            "ReviewCompletedTaskFragment" ->{
                vm.replace("CompletedTasksListFragment",null)
            }
        }
    }
}