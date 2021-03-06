package com.example.appforstudents.Presentation.Adapter.Student

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Model.CompletedTask
import com.example.appforstudents.Model.Task
import com.example.appforstudents.R

class GroupCompletedTaskListAdapter(val dateList: ArrayList<String>, val completedTasksList: ArrayList<CompletedTask>, val vm: MainViewModelForStudent) : RecyclerView.Adapter<GroupCompletedTaskListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.dateTasks)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.tasksList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.case_date_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.date.text = dateList.get(position)

        val tasks: ArrayList<CompletedTask> = arrayListOf()
        for (task in completedTasksList){
            if (task.date == dateList.get(position)){
                tasks.add(task)
            }
        }
        holder.recyclerView.layoutManager = LinearLayoutManager(vm.getApplication<Application?>().applicationContext)
        holder.recyclerView.adapter = CompletedTaskListAdapter(tasks, vm)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}