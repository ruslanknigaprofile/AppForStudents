package com.example.appforstudents.Presentation.Adapter.Teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Domain.ViewModel.Teacher.TasksListViewModel
import com.example.appforstudents.Model.Task
import com.example.appforstudents.R

class TasksListAdapter(val tasksList: ArrayList<Task>, val vm: MainViewModelForTeacher, val deleteTaskListener: DeleteTaskListener) : RecyclerView.Adapter<TasksListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTypeIcon =itemView.findViewById<ImageView>(R.id.taskTypeIcon)
        val taskType =itemView.findViewById<TextView>(R.id.taskType)
        val dateString = itemView.findViewById<TextView>(R.id.teacher_string)
        val deleteTask = itemView.findViewById<ImageView>(R.id.deleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_for_teacher, parent, false)
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

        holder.dateString.text = "Время: " + task.time

        holder.deleteTask.setOnClickListener {
            deleteTaskListener.deleteTask(task.id)
        }

        holder.itemView.setOnClickListener{
            if (task.typeTask == "Test"){
                val bundle = Bundle()
                bundle.putString("taskId", task.id)

                vm.replace("ReviewTestFragment", bundle)
            }
            else if(task.typeTask == "Answer"){
                val bundle = Bundle()
                bundle.putString("taskId", task.id)

                vm.replace("ReviewAnswerFragment", bundle)
            }
        }
    }

    interface DeleteTaskListener{
        fun deleteTask(id: String)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }
}