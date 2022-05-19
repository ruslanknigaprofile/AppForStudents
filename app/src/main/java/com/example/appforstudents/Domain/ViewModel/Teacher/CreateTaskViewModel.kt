package com.example.appforstudents.Domain.ViewModel.Teacher

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Model.Teacher
import com.example.appforstudents.Presentation.Adapter.Teacher.GalleryAdapter
import com.example.appforstudents.Presentation.Adapter.Teacher.TestAdapter
import com.example.appforstudents.Repositories.ConectorDB
import java.time.LocalTime

class CreateTaskViewModel(application: Application, val mainModel: MainViewModelForTeacher) : AndroidViewModel(application) {

    //Model
    var task = MutableLiveData(Task())
    var teacher = MutableLiveData(Teacher())
    //RecyclerViewAdapter
    var testAdapter = MutableLiveData<TestAdapter>()
    var galleryAdapter = MutableLiveData<GalleryAdapter?>()

    //Repositories
    private val connector = ConectorDB()

    init {
        getSharedPreferences()
        connector.readTeacherID(teacher.value!!.teacherId, teacher)
    }

    //setModel
    private fun getSharedPreferences(){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            teacher.value!!.teacherId = sharedPreferences.getString("id", null)!!
        }
    }

    fun writeTaskInDB(){
        task.value!!.time = LocalTime.now().toString()
        task.value!!.teacherId = teacher.value!!.teacherId
        task.value!!.teacherNames = teacher.value!!.name
        connector.writeTaskInDB(task.value!!)
    }

    //Adapters
    fun setGalleryAdapter(){
        val uriList = arrayListOf<Uri>()
        for (item in task.value?.listImageUrl!!){
            uriList.add(item.toUri())
        }
        galleryAdapter.value = GalleryAdapter(uriList, getApplication<Application>().baseContext, mainModel)
    }
    fun setTestAdapter(){
        testAdapter.value = TestAdapter(task.value?.listAnswers!!,
            task.value?.checkBoolean!!,
            object : TestAdapter.RemoveButtonListener {
                override fun removeItem(position: Int) {
                    removeAnswerInTest(position)
                }
            })
    }
    private fun removeAnswerInTest(position: Int){
        testAdapter.value!!.removeAnswer(position)
        testAdapter.value!!.notifyItemRemoved(position)
        saveDataTest()
    }
    fun addAnswerTest(){
        saveDataTest()
        if(task.value?.listAnswers!!.size <= 4) {
            testAdapter.value!!.addAnswer()
            testAdapter.value!!.notifyItemChanged(testAdapter.value!!.itemCount - 1)
        }
    }
    fun saveDataTest(){
        if (testAdapter.value != null) {
            task.value?.listAnswers?.clear()
            task.value?.checkBoolean?.clear()
            for (holder: TestAdapter.AnswerHolder in testAdapter.value!!.holders) {
                task.value?.listAnswers?.add(holder.answer.text.toString())
                task.value?.checkBoolean?.add(holder.checkBox.isChecked.toString())
            }
        }
    }

    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        mainModel.replace(course, bundle)
    }
}