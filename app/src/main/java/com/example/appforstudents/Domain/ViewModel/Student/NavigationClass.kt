package com.example.appforstudents.Domain.ViewModel.Student

import android.os.Bundle
import androidx.navigation.NavController
import com.example.appforstudents.R
import java.util.*

class NavigationClass(val navController: NavController) {

    var from = "GameTaskFragment"
    var to = ""

    fun replace(course: String, bundle: Bundle?){
        when (from) {
            "GameTaskFragment" ->{
                replaceFromGameTaskFragment(course,bundle)
            }
            "TasksListFragment" -> {
                replaceFromTaskListFragment(course, bundle)
            }
            "CompletedTasksListFragment" -> {
                replaceFromCompletedTaskListFragment(course, bundle)
            }
            "SolutionAnswerTaskFragment" -> {
                replaceFromSolutionAnswerTaskFragment(course, bundle)
            }
            "GalleryFragment" -> {
                replaceFromGalleryFragment(course, bundle)
            }
            "SolutionTestFragment" -> {
                replaceFromSolutionTestFragment(course,bundle)
            }
            "ReviewCompletedTaskFragment" -> {
                replaceFromReviewCompletedTaskFragment(course,bundle)
            }
            "SolveTrainTaskFragment" ->{
                replaceFromSolveTrainTaskFragment(course,bundle)
            }
            "TheoryFragment" ->{
                replaceFromTheoryFragment(course,bundle)
            }
        }
    }
    private fun replaceFromGameTaskFragment(course: String, bundle: Bundle?){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_gameTaskFragment_to_tasksListFragment, bundle)
                from = course
            }
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_gameTaskFragment_to_completedTasksListFragment, bundle)
                from = course
            }
            "TheoryFragment" ->{
                navController.navigate(R.id.action_gameTaskFragment_to_theoryFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromTheoryFragment(course: String, bundle: Bundle?){
        when(course){
            "SolveTrainTaskFragment" ->{
                navController.navigate(R.id.action_theoryFragment_to_solveTrainTaskFragment, bundle)
                from = course
            }
            "GameTaskFragment" ->{
                navController.navigate(R.id.action_theoryFragment_to_gameTaskFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromSolveTrainTaskFragment(course: String, bundle: Bundle?){
        when(course){
            "GameTaskFragment" ->{
                navController.navigate(R.id.action_solveTrainTaskFragment_to_gameTaskFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromTaskListFragment(course: String, bundle: Bundle?){
        when(course){
            "GameTaskFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_gameTaskFragment, bundle)
                from = course
            }
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_completedTasksListFragment, bundle)
                from = course
            }
            "SolutionAnswerTaskFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_solutionAnswerTaskFragment, bundle)
                from = course
            }
            "SolutionTestFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_solutionTestFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromCompletedTaskListFragment(course: String, bundle: Bundle?){
        when(course){
            "GameTaskFragment" ->{
                navController.navigate(R.id.action_completedTasksListFragment_to_gameTaskFragment, bundle)
                from = course
            }
            "TasksListFragment" ->{
                navController.navigate(R.id.action_completedTasksListFragment_to_tasksListFragment, bundle)
                from = course
            }
            "ReviewCompletedTaskFragment" ->{
                navController.navigate(R.id.action_completedTasksListFragment_to_reviewCompletedTaskFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromSolutionAnswerTaskFragment(course: String, bundle: Bundle?){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_solutionAnswerTaskFragment_to_tasksListFragment, bundle)
                from = course
            }
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_solutionAnswerTaskFragment_to_completedTasksListFragment, bundle)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_solutionAnswerTaskFragment_to_galleryFragment, bundle)
                to = from
                from = course
            }
        }
    }
    private fun replaceFromGalleryFragment(course: String, bundle: Bundle?){
        when(course){
            "SolutionAnswerTaskFragment" ->{
                if (to == "SolutionAnswerTaskFragment"){
                    navController.navigate(R.id.action_galleryFragment_to_solutionAnswerTaskFragment, bundle)
                    from = course
                }
            }
            "SolutionTestFragment" ->{
                if (to == "SolutionTestFragment"){
                    navController.navigate(R.id.action_galleryFragment_to_solutionTestFragment, bundle)
                    from = course
                }
            }
            "ReviewCompletedTaskFragment" ->{
                if (to == "ReviewCompletedTaskFragment"){
                    navController.navigate(R.id.action_galleryFragment_to_reviewCompletedTaskFragment, bundle)
                    from = course
                }
            }
        }
    }
    private fun replaceFromSolutionTestFragment(course: String, bundle: Bundle?){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_solutionTestFragment_to_tasksListFragment, bundle)
                from = course
            }
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_solutionTestFragment_to_completedTasksListFragment, bundle)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_solutionTestFragment_to_galleryFragment, bundle)
                to = from
                from = course
            }
        }
    }
    private fun replaceFromReviewCompletedTaskFragment(course: String, bundle: Bundle?){
        when(course){
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_reviewCompletedTaskFragment_to_completedTasksListFragment, bundle)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_reviewCompletedTaskFragment_to_galleryFragment, bundle)
                to = from
                from = course
            }
        }
    }
}