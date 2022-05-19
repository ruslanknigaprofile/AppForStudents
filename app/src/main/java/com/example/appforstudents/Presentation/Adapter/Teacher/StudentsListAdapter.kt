package com.example.appforstudents.Presentation.Adapter.Teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Model.Student
import com.example.appforstudents.R

class StudentsListAdapter(val studentsList: ArrayList<Student>, val vm: MainViewModelForTeacher) : RecyclerView.Adapter<StudentsListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val names =itemView.findViewById<TextView>(R.id.studentNames)
        val raiting =itemView.findViewById<TextView>(R.id.studentRaiting)
        val completedTask = itemView.findViewById<TextView>(R.id.completedTasks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val student = studentsList.get(position)

        holder.names.text = student.name
        holder.raiting.text = "Рейтинг: " + student.raiting
        holder.completedTask.text = "Заданий выполненно: " + student.completedTask.size

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("studentId", student.studentId)
            vm.replace("StudentFragment", bundle)
        }
    }


    override fun getItemCount(): Int {
        return studentsList.size
    }
}