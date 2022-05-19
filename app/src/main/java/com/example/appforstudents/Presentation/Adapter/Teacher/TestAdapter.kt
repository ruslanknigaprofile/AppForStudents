package com.example.appforstudents.Presentation.Adapter.Teacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.R

class TestAdapter(var answerStrings: ArrayList<String>, var booleanList: ArrayList<String>, val removeButton: RemoveButtonListener): RecyclerView.Adapter<TestAdapter.AnswerHolder>() {

    class AnswerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val answer: EditText = itemView.findViewById(R.id.answerText)
        val checkBox: CheckBox = itemView.findViewById(R.id.rightAnswerCheck)
        val removeIcon: ImageView = itemView.findViewById(R.id.deleteTestAnswer)
    }

    var answerSize = answerStrings.size
    var holders: ArrayList<AnswerHolder> = arrayListOf()

    override fun onViewRecycled(holder: AnswerHolder) {
        super.onViewRecycled(holder)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item_teacher, parent, false)
        return AnswerHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {

        if(position < answerStrings.size){
            holder.answer.setText(answerStrings.get(position))
            holder.checkBox.isChecked = booleanList.get(position).toBoolean()
        }
        else{
            holders.add(holder)

            holder.answer.setText("")
            holder.checkBox.isChecked = false
        }
        holder.removeIcon.setOnClickListener {
            removeButton.removeItem(holder.position)
        }
    }

    override fun getItemCount(): Int {
        return answerSize
    }

    fun addAnswer(){
        answerSize++
    }


    fun removeAnswer(position: Int){
        holders.removeAt(position)
        answerSize--
    }

    interface RemoveButtonListener{
        fun removeItem(position: Int)
    }
}