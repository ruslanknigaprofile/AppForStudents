package com.example.appforstudents.Presentation.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appforstudents.R
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    private lateinit var vm: ViewModel

    var topMenu: MaterialToolbar? = null
    private lateinit var botomBar: ChipNavigationBar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this).get(ViewModel::class.java)
        navController = Navigation.findNavController(this, R.id.fragment_place)
        vm.setNavigation(navController)

        init()
    }

    private fun init(){
        topMenu = findViewById(R.id.topAppBar)

        botomBar = findViewById(R.id.bNav)
        botomBar.setItemSelected(R.id.TasksMenu)
        botomBar.setOnItemSelectedListener {
            when(it){
                R.id.TasksMenu ->{
                    vm.replace("TasksListFragment")
                }
                R.id.CompletedTasksMenu ->{
                    vm.replace("CompletedTasksListFragment")
                }
            }
        }

        vm.currentFragment.observe(this){
            when(it){
                "TasksListFragment" ->{
                    botomBar.isVisible = true
                    topMenu?.isVisible = true
                    topMenu?.title = "Невыплненные задания"

                }
                "CompletedTasksListFragment" ->{
                    botomBar.setItemSelected(R.id.CompletedTasksMenu)
                    botomBar.isVisible = true
                    topMenu?.isVisible = true
                    topMenu?.title = "Выполненные задания"
                }
                "SolutionAnswerTaskFragment" ->{
                    botomBar.isVisible = false
                    topMenu?.isVisible = false
                }
                "SolutionTestFragment" ->{
                    botomBar.isVisible = false
                    topMenu?.isVisible = false
                }
                "ReviewCompletedTaskFragment" ->{
                    botomBar.isVisible = false
                    topMenu?.isVisible = false
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
                vm.replace("TasksListFragment")
            }
            "SolutionTestFragment" ->{
                vm.replace("TasksListFragment")
            }
            "GalleryFragment" ->{
                vm.replace("SolutionAnswerTaskFragment")
                vm.replace("SolutionTestFragment")
                vm.replace("ReviewCompletedTaskFragment")
            }
            "ReviewCompletedTaskFragment" ->{
                vm.replace("CompletedTasksListFragment")
            }
        }
    }
}