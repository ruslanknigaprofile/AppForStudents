package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.Adapter.Teacher.GalleryAdapter
import com.example.appforstudents.Presentation.Adapter.Teacher.ReviewTestAdapter
import com.example.appforstudents.Presentation.Adapter.Teacher.StudentAssesAdapter
import com.example.appforstudents.Repositories.ConectorDB

class ReviewTaskViewModel (application: Application, val mainModel: MainViewModelForTeacher) : AndroidViewModel(application) {

    //Model
    var task = MutableLiveData<Task>()
    var studentsList = MutableLiveData<ArrayList<Student>>()
    var possitionTask = MutableLiveData<ArrayList<Int>>()
    var imagesList = MutableLiveData<ArrayList<Uri>>()
    var studentChangeAssesListener = MutableLiveData<StudentAssesAdapter.ChangeAsses>()

    //Adapter
    var testAdapter = MutableLiveData<ReviewTestAdapter>()
    var galleryAdapter = MutableLiveData<GalleryAdapter>()
    var studentsAdapter = MutableLiveData<StudentAssesAdapter>()

    //Repositories
    val conectorDB = ConectorDB()

    //getModel
    fun getTaskById(id: String){
        conectorDB.readTask(id, task)
    }
    fun getStudentsMadeTask(id: String){
        conectorDB.readStudentsListsCompletedTask(
            id,
            studentsList,
            possitionTask,
            { setStudentsAdapter() }
        )
    }
    fun getImages(){
        conectorDB.getImages(task.value!!, imagesList)
    }
    fun changeAssesInDB(studentId: String, taskId: String){
        var getStudent = Student()
        for (student in studentsList.value!!){
            if (student.studentId == studentId){
                getStudent = student
            }
        }
        for (task in getStudent.completedTask){
            if (task.task.id == taskId){
                if (task.asses){
                    task.asses = false
                    getStudent.raiting -= 2
                }else{
                    task.asses = true
                    getStudent.raiting += 2
                }
            }
        }
        conectorDB.updateStudentInDB(getStudent)
    }

    //setAdapter
    fun setTestAdapter(){
        testAdapter.value = ReviewTestAdapter(task.value!!)
    }
    fun setSliderAdapter(){
        galleryAdapter.value = GalleryAdapter(imagesList.value!!, getApplication<Application>().baseContext, mainModel)
    }
    private fun setStudentsAdapter(){
        studentsAdapter.value = StudentAssesAdapter(
            studentsList.value!!,
            possitionTask.value!!,
            studentChangeAssesListener.value!!,
            mainModel
        )
    }


    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}