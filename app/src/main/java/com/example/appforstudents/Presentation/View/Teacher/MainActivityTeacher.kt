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
                    vm.replace("TaskListFragment", null)
                }
                R.id.AddTask ->{
                    vm.replace("CreateTestFragment",null)
                }
            }
        }

        vm.currentFragment.observe(this){
            when(it){
                "TaskListFragment" ->{
                    botomBar.isVisible = true
                }
                "CreateAnswerFragment" ->{
                }
                "CreateTestFragment" ->{
                }
                "AnswerTaskInfoFragment" -> {
                    botomBar.isVisible = false
                }
            }
        }
/*
        vm.positionTaskDeleted.observe(this){
            createSimpleDialog(
                "Удалить задание?",
                "Если хотите удалить выбранное задание нажмите 'да'.",
                { vm.deleteItemTasksListByPath() }
            )
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //vm.listUrisAdd(data.data.toString())
            //vm.setGalleryAdapter()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        when(vm.currentFragment.value){
            "AnswerTaskInfoFragment" ->{
                vm.replace("TaskListFragment",null)
            }
            "CreateTestFragment" ->{
                vm.replace("TaskListFragment",null)
            }
            "CreateAnswerFragment" ->{
                vm.replace("TaskListFragment",null)
            }

            "GalleryFragment" ->{
                vm.replace("AnswerTaskInfoFragment",null)
            }
        }
    }

    private fun createSimpleDialog(title: String, message: String, function: () -> Unit){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNegativeButton("Нет") { dialogInterface, i ->
        }
        builder.setPositiveButton("Да") { dialogInterface, i ->
            function()
        }
        builder.show()
    }
}