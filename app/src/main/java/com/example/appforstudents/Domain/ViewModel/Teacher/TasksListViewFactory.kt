package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Domain.ViewModel.Teacher.TasksListViewModel

class TasksListViewFactory (val application: Application, val viewModel: MainViewModelForTeacher) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksListViewModel(application, viewModel) as T
    }
}