package com.example.appforstudents.Repositories

import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewModel
import com.example.appforstudents.Model.*
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ConectorDB {

    private val mDataBaseInstance = FirebaseDatabase.getInstance()
    private val REF_STORAGE_ROOT: StorageReference = FirebaseStorage.getInstance().reference

    fun readStudentByID(id: String, student: MutableLiveData<Student>){
        mDataBaseInstance.getReference("StudentsID").child(id).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                student.value = snapshot.getValue(Student::class.java) as Student
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readStudentsLists(students: MutableLiveData<ArrayList<Student>>, function: () -> Unit){
        mDataBaseInstance.getReference("StudentsID").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                students.value?.clear()
                for(ds in snapshot.children){
                    val student = ds.getValue(Student::class.java) as Student
                    students.value?.add(student)
                }
                students.value?.reverse()
                function()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readStudentsListsCompletedTask(taskId: String, students: MutableLiveData<ArrayList<Student>>, possition: MutableLiveData<ArrayList<Int>>, count: MutableLiveData<Int>, function: () -> Unit){
        mDataBaseInstance.getReference("StudentsID").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                students.value = arrayListOf()
                possition.value= arrayListOf()
                count.value = 0
                for(ds in snapshot.children){
                    val student = ds.getValue(Student::class.java) as Student
                    count.value = count.value?.plus(1)
                    for ((index, task) in student.completedTask.withIndex()){
                        if (task.task.id == taskId){
                            students.value?.add(student)
                            possition.value?.add(index)
                        }
                    }
                }
                students.value?.reverse()
                possition.value?.reverse()
                function()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readTeacherID(id: String, teacher: MutableLiveData<Teacher>){
        mDataBaseInstance.getReference("TeachersID").child(id).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val getData = snapshot.getValue(Teacher::class.java) as Teacher
                teacher.value?.name = getData.name
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readCompletedTasks(studentId: String, completedTasks: MutableLiveData<ArrayList<CompletedTask>>){
        var student: Student
        mDataBaseInstance.getReference("StudentsID").child(studentId).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                student = snapshot.getValue(Student::class.java) as Student
                completedTasks.value = student.completedTask
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun readCompletedTopic(studentId: String, completedTopic: MutableLiveData<ArrayList<CompletedTopic>>){
        var student: Student
        mDataBaseInstance.getReference("StudentsID").child(studentId).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                student = snapshot.getValue(Student::class.java) as Student
                completedTopic.value = student.completedTopic
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readTasksListByCompletedTask(function: () -> Unit, tasksList: MutableLiveData<ArrayList<Task>>, completedTasks: MutableLiveData<ArrayList<CompletedTask>>){
        mDataBaseInstance.getReference("Task").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tasksList.value = arrayListOf()
                for(ds in snapshot.children){
                    val task = ds.getValue(Task::class.java) as Task
                    var check = true

                    if (completedTasks.value!!.size > 0) {
                        for (completedTask in completedTasks.value!!) {
                            if (completedTask.task.id == task.id) {
                                check = false
                            }
                        }
                        if (check){
                            tasksList.value!!.add(task)
                        }
                    }else{
                        tasksList.value!!.add(task)
                    }
                }
                tasksList.value?.reverse()
                function()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readTasksListByTeacherId(function: () -> Unit, tasksList: MutableLiveData<ArrayList<Task>>, teacherId: String){
        mDataBaseInstance.getReference("Task").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tasksList.value = arrayListOf()
                for(ds in snapshot.children){
                    val task = ds.getValue(Task::class.java) as Task
                    if(task.teacherId == teacherId){
                        tasksList.value?.add(task)
                    }
                }
                tasksList.value?.reverse()
                function()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readTask(taskId: String, task: MutableLiveData<Task>){
        mDataBaseInstance.getReference("Task").child(taskId).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.getValue(Task::class.java) != null){
                    val getTask = snapshot.getValue(Task::class.java) as Task
                    task.value = getTask
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun readRating(studentId: String, raiting: MutableLiveData<Int>){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("raiting").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                raiting.value = snapshot.getValue(Int::class.java) as Int
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getTopic(topicList: MutableLiveData<ArrayList<Topic>>){
        val topics: ArrayList<Topic> = arrayListOf()
        mDataBaseInstance.getReference("TrainTask").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    topics.add(ds.getValue(Topic::class.java) as Topic)
                }
                topicList.value = topics
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun getTheme(topicName: String, themeName: String, theme: MutableLiveData<Theme>){
        var topics = Topic()
        mDataBaseInstance.getReference("TrainTask").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val topic = ds.getValue(Topic::class.java) as Topic
                    if (topic.name == topicName){
                        topics = topic
                    }
                }
                for (themes in topics.themes){
                    if (themes.name == themeName){
                        theme.value = themes
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun updateStudentCompletedTaskInDB(studentId: String, completedTask: ArrayList<CompletedTask>){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("completedTask").setValue(completedTask)
    }
    fun updateStudentCompletedTopicInDB(studentId: String, completedTopic: ArrayList<CompletedTopic>){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("completedTopic").setValue(completedTopic)
    }
    fun updateStudentRaitingInDB(studentId: String, raiting: Int){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("raiting").setValue(raiting)
    }
    fun updateStudentInDB(student: Student){
        mDataBaseInstance.getReference("StudentsID").child(student.studentId).setValue(student)
    }

    fun getImages(task: Task, sliderImage: MutableLiveData<ArrayList<Uri>>){
        val imagesListData = arrayOfNulls<Uri>(10)
        for (i in task.listImageUrl) {
            REF_STORAGE_ROOT.child("folder_task_image")
                .child(i).downloadUrl.addOnCompleteListener {
                    try {
                        val index = it.result.toString().split("%7Cindex%7C")[1].toInt()
                        imagesListData[index] = it.result
                        val imagesList = imagesListData.filterNotNull() as ArrayList
                        /*
                        for (item in imagesListData){
                            if (item != null) {
                                imagesList.add(item)
                            }
                        }*/
                        sliderImage.value = imagesList
                    }catch (e: Exception){
                    }

                }
        }
    }

    private fun writeImages(task: Task): Task {
        for ((index, i) in task.listImageUrl.withIndex()){
            task.listImageUrl[index] = "taskId("+task.id+")|index|" + index + "|index|"
            REF_STORAGE_ROOT.child("folder_task_image")
                .child(task.listImageUrl[index]).putFile(i.toUri())
        }

        return task
    }

    fun writeTaskInDB(task: Task){
        val push = mDataBaseInstance.getReference("Task").push()
        task.id = push.key.toString()
        val postTask: Task = writeImages(task)
        push.setValue(postTask)
    }

    fun deleteTaskInDB(id: String){
        mDataBaseInstance.getReference("Task").child(id).removeValue()
    }
}