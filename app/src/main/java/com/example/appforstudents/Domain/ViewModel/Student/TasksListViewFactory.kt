package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class TasksListViewFactory(val application: Application, val viewModel: MainViewModelForStudent) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksListViewModel(application, viewModel) as T
    }
}