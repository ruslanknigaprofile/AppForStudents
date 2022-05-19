package com.example.appforstudents.Presentation.Adapter.Teacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Model.Task
import com.example.appforstudents.R

class ReviewTestAdapter(val task: Task): RecyclerView.Adapter<ReviewTestAdapter.AnswerHolder>() {

    class AnswerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val answer: TextView = itemView.findViewById(R.id.answerTestItem)
        val assesImage: ImageView = itemView.findViewById(R.id.assesImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item_for_review, parent, false)
        return AnswerHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.answer.text = task.listAnswers.get(position)
        if (task.checkBoolean.get(position).toBoolean()){
            holder.assesImage.setImageResource(R.drawable.ic_sharp_check_24)
        }else{
            holder.assesImage.setImageResource(R.drawable.ic_sharp_close_24)
        }
    }

    override fun getItemCount(): Int {
        return task.listAnswers.size
    }
}