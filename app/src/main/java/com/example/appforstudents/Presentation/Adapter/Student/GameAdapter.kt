package com.example.appforstudents.Presentation.Adapter.Student

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Model.Student
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Model.Topic
import com.example.appforstudents.R

class GameAdapter (val student: Student, val topicList: ArrayList<Topic>, val vm: MainViewModelForStudent) : RecyclerView.Adapter<GameAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val classNumber = itemView.findViewById<TextView>(R.id.classNumber)

        val item1View = itemView.findViewById<RelativeLayout>(R.id.item1View)
        val item2View = itemView.findViewById<RelativeLayout>(R.id.item2View)
        val item3View = itemView.findViewById<RelativeLayout>(R.id.item3View)
        val item4View = itemView.findViewById<RelativeLayout>(R.id.item4View)

        val name1View = itemView.findViewById<TextView>(R.id.name1View)
        val name2View = itemView.findViewById<TextView>(R.id.name2View)
        val name3View = itemView.findViewById<TextView>(R.id.name3View)
        val name4View = itemView.findViewById<TextView>(R.id.name4View)

        val view1Star = itemView.findViewById<LinearLayout>(R.id.view1Star)
        val view2Star = itemView.findViewById<LinearLayout>(R.id.view2Star)
        val view3Star = itemView.findViewById<LinearLayout>(R.id.view3Star)
        val view4Star = itemView.findViewById<LinearLayout>(R.id.view4Star)

        val asses1Star = itemView.findViewById<TextView>(R.id.asses1Star)
        val asses2Star = itemView.findViewById<TextView>(R.id.asses2Star)
        val asses3Star = itemView.findViewById<TextView>(R.id.asses3Star)
        val asses4Star = itemView.findViewById<TextView>(R.id.asses4Star)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.class_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val currentTopic = topicList.get(position)

        holder.classNumber.text = currentTopic.name

        holder.name1View.setText(currentTopic.themes.get(0).name + "\n" + currentTopic.themes.get(0).symbol)
        holder.name2View.setText(currentTopic.themes.get(1).name + "\n" + currentTopic.themes.get(1).symbol)
        holder.name3View.setText(currentTopic.themes.get(2).name + "\n" + currentTopic.themes.get(2).symbol)
        holder.name4View.setText(currentTopic.themes.get(3).name + "\n" + currentTopic.themes.get(3).symbol)

        holder.view1Star.isVisible = false
        holder.view2Star.isVisible = false
        holder.view3Star.isVisible = false
        holder.view4Star.isVisible = false

        for (topic in student.completedTopic){
            for ((index,themes) in topic.topic.themes.withIndex()){
                if (themes.name == currentTopic.themes.get(0).name){
                    holder.asses1Star.text = topic.themeStar.get(index).toString()
                    holder.view1Star.isVisible = true
                }

                if (themes.name == currentTopic.themes.get(1).name){
                    holder.asses2Star.text = topic.themeStar.get(index).toString()
                    holder.view2Star.isVisible = true
                }

                if (themes.name == currentTopic.themes.get(2).name){
                    holder.asses3Star.text = topic.themeStar.get(index).toString()
                    holder.view3Star.isVisible = true
                }

                if (themes.name == currentTopic.themes.get(3).name){
                    holder.asses4Star.text = topic.themeStar.get(index).toString()
                    holder.view4Star.isVisible = true
                }
            }
        }

        holder.item1View.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("topic", currentTopic.name)
            bundle.putString("themes", currentTopic.themes.get(0).name)

            vm.replace("SolveTrainTaskFragment", bundle)
        }
        holder.item2View.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("topic", currentTopic.name)
            bundle.putString("themes", currentTopic.themes.get(1).name)

            vm.replace("SolveTrainTaskFragment", bundle)
        }
        holder.item3View.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("topic", currentTopic.name)
            bundle.putString("themes", currentTopic.themes.get(2).name)

            vm.replace("SolveTrainTaskFragment", bundle)
        }
        holder.item4View.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("topic", currentTopic.name)
            bundle.putString("themes", currentTopic.themes.get(3).name)

            vm.replace("SolveTrainTaskFragment", bundle)
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }
}