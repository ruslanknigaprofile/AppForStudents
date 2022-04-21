package com.example.appforstudents.Domain.ViewModel

import androidx.navigation.NavController
import com.example.appforstudents.R

class NavigationClass(val navController: NavController) {

    var from = "TasksListFragment"
    var to = ""

    fun replace(course: String){
        when(from){
            "TasksListFragment" ->{
                replaceFromTaskListFragment(course)
            }
            "CompletedTasksListFragment" ->{
                replaceFromCompletedTaskListFragment(course)
            }
            "SolutionAnswerTaskFragment" ->{
                replaceFromSolutionAnswerTaskFragment(course)
            }
            "GalleryFragment" ->{
                replaceFromGalleryFragment(course)
            }
            "SolutionTestFragment" ->{
                replaceFromSolutionTestFragment(course)
            }
            "ReviewCompletedTaskFragment" ->{
                replaceFromReviewCompletedTaskFragment(course)
            }
        }
    }

    private fun replaceFromTaskListFragment(course: String){
        when(course){
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_completedTasksListFragment)
                from = course
            }
            "SolutionAnswerTaskFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_solutionAnswerTaskFragment)
                from = course
            }
            "SolutionTestFragment" ->{
                navController.navigate(R.id.action_tasksListFragment_to_solutionTestFragment)
                from = course
            }
        }
    }
    private fun replaceFromCompletedTaskListFragment(course: String){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_completedTasksListFragment_to_tasksListFragment)
                from = course
            }
            "ReviewCompletedTaskFragment" ->{
                navController.navigate(R.id.action_completedTasksListFragment_to_reviewCompletedTaskFragment)
                from = course
            }
        }
    }
    private fun replaceFromSolutionAnswerTaskFragment(course: String){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_solutionAnswerTaskFragment_to_tasksListFragment)
                from = course
            }
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_solutionAnswerTaskFragment_to_completedTasksListFragment)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_solutionAnswerTaskFragment_to_galleryFragment)
                to = from
                from = course
            }
        }
    }
    private fun replaceFromGalleryFragment(course: String){
        when(course){
            "SolutionAnswerTaskFragment" ->{
                if (to == "SolutionAnswerTaskFragment"){
                    navController.navigate(R.id.action_galleryFragment_to_solutionAnswerTaskFragment)
                    from = course
                }
            }
            "SolutionTestFragment" ->{
                if (to == "SolutionTestFragment"){
                    navController.navigate(R.id.action_galleryFragment_to_solutionTestFragment)
                    from = course
                }
            }
            "ReviewCompletedTaskFragment" ->{
                if (to == "ReviewCompletedTaskFragment"){
                    navController.navigate(R.id.action_galleryFragment_to_reviewCompletedTaskFragment)
                    from = course
                }
            }
        }
    }
    private fun replaceFromSolutionTestFragment(course: String){
        when(course){
            "TasksListFragment" ->{
                navController.navigate(R.id.action_solutionTestFragment_to_tasksListFragment)
                from = course
            }
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_solutionTestFragment_to_completedTasksListFragment)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_solutionTestFragment_to_galleryFragment)
                to = from
                from = course
            }
        }
    }
    private fun replaceFromReviewCompletedTaskFragment(course: String){
        when(course){
            "CompletedTasksListFragment" ->{
                navController.navigate(R.id.action_reviewCompletedTaskFragment_to_completedTasksListFragment)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_reviewCompletedTaskFragment_to_galleryFragment)
                to = from
                from = course
            }
        }
    }
}