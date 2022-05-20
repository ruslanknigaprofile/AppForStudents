package com.example.appforstudents.Presentation.Adapter.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewModel
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.View.Student.MainActivityStudent
import com.example.appforstudents.R

class TasksListAdapter(val tasksList: ArrayList<Task>, val vm: MainViewModelForStudent, val startTaskListener: StartTaskListener) : RecyclerView.Adapter<TasksListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
        val teacherString = itemView.findViewById<TextView>(R.id.teacher_string)
        val asses = itemView.findViewById<TextView>(R.id.asses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_for_student, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasksList.get(position)

        if (task.typeTask == "Test"){
            holder.taskTypeIcon.setImageResource(R.drawable.test_icon)
            holder.taskType.text = "Тест"
        }
        else if(task.typeTask == "Answer"){
            holder.taskTypeIcon.setImageResource(R.drawable.task_icon)
            holder.taskType.text = "Задача"
        }

        holder.teacherString.text = "Преподаватель: " + task.teacherNames
        holder.asses.text = "Задано в " + task.time

        holder.itemView.setOnClickListener{
            startTaskListener.startTask(task)
        }
    }

    interface StartTaskListener{
        fun startTask(task: Task)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }
}