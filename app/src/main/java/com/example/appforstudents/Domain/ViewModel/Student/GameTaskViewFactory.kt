package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameTaskViewFactory (val application: Application, val viewModel: MainViewModelForStudent) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameTaskViewModel(application, viewModel) as T
    }
}