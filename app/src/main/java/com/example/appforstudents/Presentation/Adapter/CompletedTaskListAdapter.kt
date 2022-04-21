package com.example.appforstudents.Presentation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R

class CompletedTaskListAdapter(val vm: ViewModel) : RecyclerView.Adapter<CompletedTaskListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
        val asses =itemView.findViewById<TextView>(R.id.asses)
        val taskMarker =itemView.findViewById<ImageView>(R.id.taskMarker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val completedTask = vm.student.value!!.completedTask

        if (completedTask.task.get(position).typeTask == "Test"){
            holder.taskTypeIcon.setImageResource(R.drawable.test_icon)
            holder.taskType.text = "Тест"
        }
        else if(completedTask.task.get(position).typeTask == "Answer"){
            holder.taskTypeIcon.setImageResource(R.drawable.task_icon)
            holder.taskType.text = "Задача"
        }

        if (completedTask.asses.get(position)){
            holder.taskMarker.setImageResource(R.drawable.task_marker_asses)
            holder.asses.text = "Сдан"
        } else if(!completedTask.asses.get(position)){
            holder.taskMarker.setImageResource(R.drawable.task_marker_not_asses)
            holder.asses.text = "Не сдан"
        }

        holder.itemView.setOnClickListener {
            vm.possitionReviewCompletedTask.value = position
            vm.replace("ReviewCompletedTaskFragment")
            vm.disposeSolutionTask()
        }
    }

    override fun getItemCount(): Int {
        if (vm.completedTasksList.value != null)
            return vm.student.value!!.completedTask.task.size
        else
            return 0
    }
}