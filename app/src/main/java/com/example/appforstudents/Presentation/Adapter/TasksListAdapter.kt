package com.example.appforstudents.Presentation.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewModel
import com.example.appforstudents.R

class TasksListAdapter(val vm: TasksListViewModel) : RecyclerView.Adapter<TasksListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
        val teacherString = itemView.findViewById<TextView>(R.id.teacher_string)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_for_student, parent, false)
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

        val teacherNamesAndDate = "Дано: " + vm.tasksList.value!!.get(position).teacherNames + vm.tasksList.value!!.get(position).date
        holder.teacherString.text = teacherNamesAndDate

        holder.itemView.setOnClickListener{
            if (vm.tasksList.value!!.get(position).typeTask == "Test"){
                val bundle = Bundle()
                bundle.putString("positionSolutionTestTask", vm.tasksList.value!!.get(position).id)

                vm.replace("SolutionTestFragment", bundle)
            }
            else if(vm.tasksList.value!!.get(position).typeTask == "Answer"){
                val bundle = Bundle()
                bundle.putString("positionSolutionAnswerTask", vm.tasksList.value!!.get(position).id)

                vm.replace("SolutionAnswerTaskFragment", bundle)
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