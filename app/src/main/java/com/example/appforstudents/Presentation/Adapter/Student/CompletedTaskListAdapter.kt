package com.example.appforstudents.Presentation.Adapter.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.CompletedTasksListViewModel
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.R

class CompletedTaskListAdapter(val completedTasksList: ArrayList<CompletedTask>, val vm: MainViewModelForStudent) : RecyclerView.Adapter<CompletedTaskListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
        val teacherString = itemView.findViewById<TextView>(R.id.teacher_string)
        val asses = itemView.findViewById<TextView>(R.id.asses)
        val taskMarker = itemView.findViewById<ImageView>(R.id.taskMarker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_for_student, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = completedTasksList.get(position).task
        val completedTask = completedTasksList.get(position)

        if (task.typeTask == "Test"){
            holder.taskTypeIcon.setImageResource(R.drawable.test_icon)
            holder.taskType.text = "Тест"
        }
        else if(task.typeTask == "Answer"){
            holder.taskTypeIcon.setImageResource(R.drawable.task_icon)
            holder.taskType.text = "Задача"
        }

        holder.teacherString.text = "Преподаватель: " + task.teacherNames

        if (completedTask.asses){
            holder.taskMarker.setImageResource(R.drawable.ic_baseline_circle_24_asses)
            holder.asses.text = "Сдан в " + completedTask.time
        } else if(!completedTask.asses){
            holder.taskMarker.setImageResource(R.drawable.ic_baseline_circle_24_not_asses)
            holder.asses.text = "Не сдан в " + completedTask.time
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("positionReviewCompletedTask", position.toString())

            vm.replace("ReviewCompletedTaskFragment", bundle)
        }
    }

    override fun getItemCount(): Int {
        return completedTasksList.size
    }
}