package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReviewTaskViewFactory (val application: Application, val viewModel: MainViewModelForTeacher) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReviewTaskViewModel(application, viewModel) as T
    }
}