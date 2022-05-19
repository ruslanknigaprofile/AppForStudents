package com.example.appforstudents.Presentation.Adapter.Teacher

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Model.Student
import com.example.appforstudents.R

class StudentAssesAdapter(val studentsList: ArrayList<Student>, val positionTask: ArrayList<Int>, val changeAsses: ChangeAsses, val vm: MainViewModelForTeacher) : RecyclerView.Adapter<StudentAssesAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val names =itemView.findViewById<TextView>(R.id.studentNames)
        val answer =itemView.findViewById<TextView>(R.id.studentAnswer)
        val asses =itemView.findViewById<TextView>(R.id.studentAsses)
        val changeAsses =itemView.findViewById<TextView>(R.id.changeAsses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_asses_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val student = studentsList.get(position)
        val pos = positionTask.get(position)

        holder.names.text = student.name
        holder.answer.text = student.completedTask.get(pos).answer
        if(student.completedTask.get(pos).asses){
            holder.asses.text = "Сдал"
            holder.asses.setTextColor(Color.parseColor("#58a969"))
        }else{
            holder.asses.text = "Не сдал"
            holder.asses.setTextColor(Color.parseColor("#dc5861"))
        }

        holder.changeAsses.setOnClickListener {
            changeAsses.changeAsses(
                student.studentId,
                student.completedTask.get(pos).task.id
            )
        }
    }

    interface ChangeAsses{
        fun changeAsses(studentId: String, taskId: String)
    }


    override fun getItemCount(): Int {
        return studentsList.size
    }
}