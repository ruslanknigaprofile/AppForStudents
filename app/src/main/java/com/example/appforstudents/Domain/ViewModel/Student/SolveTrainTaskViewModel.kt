package com.example.appforstudents.Domain.ViewModel.Student

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appforstudents.Model.*
import com.example.appforstudents.Repositories.ConectorDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class SolveTrainTaskViewModel(application: Application, val mainModel: MainViewModelForStudent) : AndroidViewModel(application) {

    //Model
    private var student = MutableLiveData(Student())
    var theme = MutableLiveData<Theme>()
    var completedTopics = MutableLiveData<ArrayList<CompletedTopic>>(arrayListOf())
    var firstNumber = MutableLiveData<Int>()
    var secondNumber = MutableLiveData<Int>()
    var answer = MutableLiveData(0)
    var arrayListAnswers = MutableLiveData<ArrayList<Int>>()
    var shuffled = MutableLiveData<Boolean>()
    var getAnswers = MutableLiveData(0)
    var rightAnswers = MutableLiveData(0)
    var topicName = MutableLiveData<String>()
    var themeName = MutableLiveData<String>()
    var givedTask = MutableLiveData(false)

    //Repositories
    private val connector = ConectorDB()


    init {
        getSharedPreferences()
    }

    //setModel
    private fun getSharedPreferences(){
        val sharedPreferences = getApplication<Application>().baseContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("id", null) != null){
            student.value!!.studentId = sharedPreferences.getString("id", null)!!
        }
    }
    fun getThemes(){
        connector.readCompletedTopic(student.value!!.studentId, completedTopics)
        connector.getTheme(topicName.value!!, themeName.value!!, theme)
    }
    private fun getNumber(){
        firstNumber.value = Random.nextInt(theme.value!!.startNumber, theme.value!!.endNumber)
        secondNumber.value = Random.nextInt(theme.value!!.startNumber, theme.value!!.endNumber)

        if (theme.value!!.symbol == "+"){
            answer.value = firstNumber.value!! + secondNumber.value!!
        }else if(theme.value!!.symbol == "-"){
            answer.value = firstNumber.value!!
            firstNumber.value = firstNumber.value!! + secondNumber.value!!
        }else if(theme.value!!.symbol == "*"){
            answer.value = firstNumber.value!! * secondNumber.value!!
        }else if(theme.value!!.symbol == "/"){
            answer.value = firstNumber.value!!
            firstNumber.value = secondNumber.value!! * firstNumber.value!!
        }
    }

    fun randomTask() {
        getNumber()
        var fault = 0
        do {
            var replay = false
            arrayListAnswers.value = arrayListOf()
            arrayListAnswers.value!!.add(answer.value!!)
            arrayListAnswers.value!!.add(
                Random.nextInt(
                    theme.value!!.startNumber,
                    (answer.value!! + fault) * 2
                )
            )
            arrayListAnswers.value!!.add(
                Random.nextInt(
                    theme.value!!.startNumber,
                    (answer.value!! + fault) * 2
                )
            )
            arrayListAnswers.value!!.add(
                Random.nextInt(
                    theme.value!!.startNumber,
                    (answer.value!! + fault) * 2
                )
            )
            fault++
            for (number1 in arrayListAnswers.value!!) {
                var rep = 0
                for (number2 in arrayListAnswers.value!!) {
                    if (number1 == number2) {
                        rep++
                    }
                }
                if (rep > 1) {
                    replay = true
                }
            }

        } while (replay)



        arrayListAnswers.value!!.shuffle()
        shuffled.value = true
    }




    fun giveTask() {
        viewModelScope.launch(Dispatchers.Main) {
            var haveTopic = false
            var haveTheme = false

            for (topic in completedTopics.value!!) {
                if (topic.topic.name == topicName.value) {
                    haveTopic = true
                    for (theme in topic.topic.themes) {
                        if (theme.name == themeName.value) {
                            haveTheme = true
                        }
                    }
                }
            }

            if (!haveTopic) {
                val completedTopic = CompletedTopic()
                completedTopic.topic.name = topicName.value!!
                completedTopic.topic.themes.add(theme.value!!)
                completedTopic.themeStar.add(rightAnswers.value!!)

                completedTopics.value!!.add(completedTopic)
            } else if (haveTopic && !haveTheme) {
                for (topic in completedTopics.value!!) {
                    if (topic.topic.name == topicName.value!!) {
                        topic.topic.themes.add(theme.value!!)
                        topic.themeStar.add(rightAnswers.value!!)
                    }
                }
            } else if (haveTopic && haveTheme) {
                for (topic in completedTopics.value!!) {
                    if (topic.topic.name == topicName.value) {
                        for ((index, theme) in topic.topic.themes.withIndex()) {
                            if (theme.name == themeName.value) {
                                if (topic.themeStar[index] < rightAnswers.value!!) {
                                    topic.themeStar[index] = rightAnswers.value!!
                                }
                            }
                        }
                    }
                }
            }

            connector.updateStudentCompletedTopicInDB(
                student.value!!.studentId,
                completedTopics.value!!
            )
            givedTask.value = true
        }
    }


    //Navigation
    fun replace(course: String, bundle: Bundle?) {
        viewModelScope.launch(Dispatchers.Main){
            mainModel.replace(course, bundle)
        }
    }
}