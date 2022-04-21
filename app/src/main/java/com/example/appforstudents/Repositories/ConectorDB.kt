package com.example.appforstudents.Repositories

import android.net.Uri
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConectorDB {

    private val mDataBaseInstance = FirebaseDatabase.getInstance()
    private val REF_STORAGE_ROOT: StorageReference = FirebaseStorage.getInstance().reference

    fun readStudentID(vm: ViewModel){
        var student: Student
        mDataBaseInstance.getReference("StudentsID").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    student = ds.getValue(Student::class.java) as Student
                    if (student.studentId == vm.student.value?.studentId){
                        vm.student.value = student
                        vm.getTasksList()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readTasksList(function: () -> Unit, vm: ViewModel){
        mDataBaseInstance.getReference("Task").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                vm.tasksList.value?.clear()
                for(ds in snapshot.children){
                    val task = ds.getValue(Task::class.java) as Task
                    var check = true

                    if (vm.student.value!!.completedTask.task.size > 0) {
                        for (completedTask in vm.student.value!!.completedTask.task) {
                            if (completedTask.id == task.id) {
                                check = false
                            }
                        }
                        if (check){
                            vm.tasksList.value!!.add(task)
                        }
                    }else{
                        vm.tasksList.value!!.add(task)
                    }
                }
                function()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun updateStudentCompletedTaskInDB(student: Student){
        mDataBaseInstance.getReference("StudentsID").child(student.studentId).setValue(student)
    }


    fun getImages(task: Task, function: () -> Unit, vm: ViewModel){
        for (i in task.listImageUrl) {
            REF_STORAGE_ROOT.child("folder_task_image")
                .child(i).downloadUrl.addOnCompleteListener {
                    vm.sliderImage.value!!.add(it.result)
                    function()
                }
        }
    }
}