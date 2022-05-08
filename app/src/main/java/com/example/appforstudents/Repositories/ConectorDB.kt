package com.example.appforstudents.Repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewModel
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ConectorDB {

    private val mDataBaseInstance = FirebaseDatabase.getInstance()
    private val REF_STORAGE_ROOT: StorageReference = FirebaseStorage.getInstance().reference

    fun readStudentID(vm: TasksListViewModel){
        var student: Student
        mDataBaseInstance.getReference("StudentsID").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    student = ds.getValue(Student::class.java) as Student
                    if (student.studentId == vm.student.value?.studentId){
                        vm.student.value = student
                        //vm.getTasksList()
                    }
                }
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
                function()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readTask(taskId: String, task: MutableLiveData<Task>){

        mDataBaseInstance.getReference("Task").child(taskId).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val getTask = snapshot.getValue(Task::class.java) as Task
                task.value = getTask
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun readRating(studentId: String, raiting: MutableLiveData<Int>){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("raiting").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val getRaiting = snapshot.getValue(Int::class.java) as Int
                raiting.value = getRaiting
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun updateStudentCompletedTaskInDB(studentId: String, completedTask: ArrayList<CompletedTask>){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("completedTask").setValue(completedTask)
    }
    fun updateStudentRaitingInDB(studentId: String, raiting: Int){
        mDataBaseInstance.getReference("StudentsID").child(studentId)
            .child("raiting").setValue(raiting)
    }

    fun getImages(task: Task, function: () -> Unit, sliderImage: MutableLiveData<ArrayList<Uri>>){
        for (i in task.listImageUrl) {
            REF_STORAGE_ROOT.child("folder_task_image")
                .child(i).downloadUrl.addOnCompleteListener {
                    sliderImage.value?.add(it.result)
                    function()
                }
        }
    }
}