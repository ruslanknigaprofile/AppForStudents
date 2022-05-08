package com.example.appforstudents.Presentation.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.CompletedTasksListViewModel
import com.example.appforstudents.R

class CompletedTaskListAdapter(val vm: CompletedTasksListViewModel) : RecyclerView.Adapter<CompletedTaskListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
        val asses =itemView.findViewById<TextView>(R.id.asses)
        val taskMarker =itemView.findViewById<ImageView>(R.id.taskMarker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_for_student, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val completedTask = vm.completedTasksList.value!!

        if (completedTask.get(position).task.typeTask == "Test"){
            holder.taskTypeIcon.setImageResource(R.drawable.test_icon)
            holder.taskType.text = "Тест"
        }
        else if(completedTask.get(position).task.typeTask == "Answer"){
            holder.taskTypeIcon.setImageResource(R.drawable.task_icon)
            holder.taskType.text = "Задача"
        }

        if (completedTask.get(position).asses){
            holder.taskMarker.setImageResource(R.drawable.task_marker_asses)
            holder.asses.text = "Сдан"
        } else if(!completedTask.get(position).asses){
            holder.taskMarker.setImageResource(R.drawable.task_marker_not_asses)
            holder.asses.text = "Не сдан"
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("positionReviewCompletedTask", position.toString())

            vm.replace("ReviewCompletedTaskFragment", bundle)
        }
    }

    override fun getItemCount(): Int {
        if (vm.completedTasksList.value != null)
            return vm.completedTasksList.value!!.size
        else
            return 0
    }
}