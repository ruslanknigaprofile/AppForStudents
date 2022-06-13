package com.example.appforstudents.Presentation.View.Student

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appforstudents.R
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Model.Constant.Companion.STUDENT_TOPIC
import com.example.appforstudents.Model.Constant.Companion.TEACHER_TOPIC
import com.example.appforstudents.Model.NotificationData
import com.example.appforstudents.Model.PushNotification
import com.example.appforstudents.Repositories.MyFirebaseMessagingService
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
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
        vm.setBuilder(AlertDialog.Builder(this))
        init()
    }

    private fun init(){
        botomBar = findViewById(R.id.bNav)
        botomBar.setItemSelected(R.id.TrainTasksMenu)
        botomBar.setOnItemSelectedListener {
            when(it){
                R.id.TrainTasksMenu ->{
                    vm.replace("GameTaskFragment", null)

                }
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
                "GameTaskFragment" ->{
                    botomBar.isVisible = true
                }
                "SolveTrainTaskFragment" ->{
                    botomBar.isVisible = false
                }
                "TheoryFragment" ->{
                    botomBar.isVisible = false
                }
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

        FirebaseMessaging.getInstance().subscribeToTopic(STUDENT_TOPIC)
    }

    override fun onBackPressed() {
        when(vm.currentFragment.value){
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
            "SolveTrainTaskFragment" ->{
                vm.createSimpleDialog("Выйти",
                "Вы уверены что хотите прекратить выполнение задания?"
                ) { vm.replace("GameTaskFragment", null) }
            }
            "TheoryFragment" ->{
                vm.replace("GameTaskFragment", null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}