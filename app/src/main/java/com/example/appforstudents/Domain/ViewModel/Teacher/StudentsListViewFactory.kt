package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentsListViewFactory(val application: Application, val viewModel: MainViewModelForTeacher) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentsListViewModel(application, viewModel) as T
    }
}