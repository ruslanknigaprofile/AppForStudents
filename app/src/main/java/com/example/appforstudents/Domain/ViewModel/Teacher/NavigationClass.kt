package com.example.appforstudents.Domain.ViewModel.Teacher

import android.os.Bundle
import androidx.navigation.NavController
import com.example.appforstudents.R

class NavigationClass (val navController: NavController) {

    var from = "TasksListFragment"
    var to = ""

    fun replace(course: String, bundle: Bundle?) {
        when (from) {
            "TasksListFragment" -> {
                replaceFromTaskListFragment(course, bundle)
            }
            "CreateTestFragment" -> {
                replaceFromCreateTestFragment(course, bundle)
            }
            "CreateAnswerFragment" -> {
                replaceFromCreateAnswerFragment(course, bundle)
            }
            "GalleryFragment" -> {
                replaceFromGalleryFragment(course, bundle)
            }
            "StudentsListFragment" ->{
                replaceFromStudentsListFragment(course,bundle)
            }
            "StudentFragment" ->{
                replaceFromStudentFragment(course,bundle)
            }
            "ReviewTestFragment" ->{
                replaceFromReviewTestFragment(course,bundle)
            }
            "ReviewAnswerFragment" ->{
                replaceFromReviewAnswerFragment(course,bundle)
            }
        }
    }

    private fun replaceFromTaskListFragment(course: String, bundle: Bundle?){
        when(course){
            "CreateTestFragment" ->{
                navController.navigate(R.id.action_tasksListFragment2_to_createTestFragment, bundle)
                from = course
            }
            "StudentsListFragment" ->{
                navController.navigate(R.id.action_tasksListFragment2_to_studentsListFragment, bundle)
                from = course
            }
            "ReviewTestFragment" ->{
                navController.navigate(R.id.action_tasksListFragment2_to_reviewTestFragment, bundle)
                from = course
            }
            "ReviewAnswerFragment" ->{
                navController.navigate(R.id.action_tasksListFragment2_to_reviewAnswerFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromCreateTestFragment(course: String, bundle: Bundle?){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_createTestFragment_to_tasksListFragment2, bundle)
                from = course
            }
            "CreateAnswerFragment" ->{
                navController.navigate(R.id.action_createTestFragment_to_createAnswerFragment, bundle)
                from = course
            }
            "StudentsListFragment" ->{
                navController.navigate(R.id.action_createTestFragment_to_studentsListFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromCreateAnswerFragment(course: String, bundle: Bundle?){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_createAnswerFragment_to_tasksListFragment2, bundle)
                from = course
            }
            "CreateTestFragment" ->{
                navController.navigate(R.id.action_createAnswerFragment_to_createTestFragment, bundle)
                from = course
            }
            "StudentsListFragment" ->{
                navController.navigate(R.id.action_createAnswerFragment_to_studentsListFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromStudentsListFragment(course: String, bundle: Bundle?){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_studentsListFragment_to_tasksListFragment2, bundle)
                from = course
            }
            "CreateTestFragment" ->{
                navController.navigate(R.id.action_studentsListFragment_to_createTestFragment, bundle)
                from = course
            }
            "StudentFragment" ->{
                navController.navigate(R.id.action_studentsListFragment_to_studentFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromStudentFragment(course: String, bundle: Bundle?){
        when(course){
            "StudentsListFragment" ->{
                navController.navigate(R.id.action_studentFragment_to_studentsListFragment, bundle)
                from = course
            }
        }
    }
    private fun replaceFromGalleryFragment(course: String, bundle: Bundle?) {
        when (course) {
            "ReviewTestFragment" -> {
                if (to == "ReviewTestFragment") {
                    from = course
                    navController.navigate(R.id.action_galleryFragment2_to_reviewTestFragment, bundle)
                }
            }
            "ReviewAnswerFragment" -> {
                if (to == "ReviewAnswerFragment") {
                    from = course
                    navController.navigate(R.id.action_galleryFragment2_to_reviewAnswerFragment, bundle)
                }
            }
        }
    }
    private fun replaceFromReviewTestFragment(course: String, bundle: Bundle?){
        when(course){
            "GalleryFragment" ->{
                navController.navigate(R.id.action_reviewTestFragment_to_galleryFragment2, bundle)
                to = from
                from = course
            }
            "TasksListFragment" ->{
                navController.navigate(R.id.action_reviewTestFragment_to_tasksListFragment2, bundle)
                from = course
            }
        }
    }
    private fun replaceFromReviewAnswerFragment(course: String, bundle: Bundle?){
        when(course){
            "GalleryFragment" ->{
                navController.navigate(R.id.action_reviewAnswerFragment_to_galleryFragment2, bundle)
                to = from
                from = course
            }
            "TasksListFragment" ->{
                navController.navigate(R.id.action_reviewAnswerFragment_to_tasksListFragment2, bundle)
                from = course
            }
        }
    }

}