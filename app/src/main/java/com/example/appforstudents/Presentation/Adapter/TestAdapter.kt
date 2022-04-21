package com.example.appforstudents.Presentation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R

class TestAdapter(val vm: ViewModel): RecyclerView.Adapter<TestAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val checkBox = itemView.findViewById<CheckBox>(R.id.answerCheckBox)
        val textView = itemView.findViewById<TextView>(R.id.answerTestItem)
    }

    var holders: ArrayList<TaskHolder> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return TestAdapter.TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.textView.text = vm.tasksList.value?.get(vm.possitionSolutionTask.value!!)?.listAnswers?.get(position)

        holder.itemView.setOnClickListener {
            holder.checkBox.isChecked = !holder.checkBox.isChecked
        }

        holders.add(holder)
    }

    override fun getItemCount(): Int {
        return vm.tasksList.value?.get(vm.possitionSolutionTask.value!!)?.listAnswers?.size ?: 0
    }
}