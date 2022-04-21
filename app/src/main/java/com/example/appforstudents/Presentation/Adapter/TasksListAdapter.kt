package com.example.appforstudents.Presentation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R

class TasksListAdapter(val vm: ViewModel) : RecyclerView.Adapter<TasksListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        if (vm.tasksList.value!!.get(position).typeTask == "Test"){
            holder.taskTypeIcon.setImageResource(R.drawable.test_icon)
            holder.taskType.text = "Тест"
        }
        else if(vm.tasksList.value!!.get(position).typeTask == "Answer"){
            holder.taskTypeIcon.setImageResource(R.drawable.task_icon)
            holder.taskType.text = "Задача"
        }

        holder.itemView.setOnClickListener{
            if (vm.tasksList.value!!.get(position).typeTask == "Test"){
                vm.possitionSolutionTask.value = position
                vm.disposeSolutionTask()
                vm.replace("SolutionTestFragment")
            }
            else if(vm.tasksList.value!!.get(position).typeTask == "Answer"){
                vm.possitionSolutionTask.value = position
                vm.disposeSolutionTask()
                vm.replace("SolutionAnswerTaskFragment")
            }
        }
    }

    override fun getItemCount(): Int {
        if (vm.tasksList.value != null)
            return vm.tasksList.value!!.size
        else
            return 0
    }
}