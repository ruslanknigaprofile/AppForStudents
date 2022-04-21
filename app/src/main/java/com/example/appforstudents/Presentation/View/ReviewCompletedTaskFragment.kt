package com.example.appforstudents.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R


class ReviewCompletedTaskFragment : Fragment() {

    private lateinit var vm: ViewModel

    var linearLayout: LinearLayout? = null
    var taskBody: TextView? = null
    var rightAnswer: TextView? = null
    var giveAnswer: TextView? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review_completed_task, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        val completedTask = vm.student.value?.completedTask
        val possition = vm.possitionReviewCompletedTask.value!!

        linearLayout = view.findViewById(R.id.background_image_view)
        if (completedTask!!.asses.get(possition)){
            linearLayout?.setBackgroundColor(getResources().getColor(R.color.asses))
        }else{
            linearLayout?.setBackgroundColor(getResources().getColor(R.color.dont_asses))
        }

        taskBody = view.findViewById(R.id.taskBody)
        taskBody?.text = completedTask.task.get(possition).bodyTask

        rightAnswer = view.findViewById(R.id.rightAnswer)
        if (completedTask.task.get(possition).typeTask == "Test"){
            var answer = ""
            for ((index, i) in completedTask.task.get(possition).checkBoolean.withIndex()){
                if (i.toBoolean()){
                    answer += completedTask.task.get(possition).listAnswers.get(index) + "."
                }
            }
            rightAnswer?.text = answer
        }else{
            rightAnswer?.text = completedTask.task.get(possition).listAnswers.get(0) + "."
        }

        giveAnswer = view.findViewById(R.id.giveAnswer)
        giveAnswer?.text = completedTask.answer.get(possition)

        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager

        if (vm.galleryAdapter.value == null) {
            vm.getImagesForReview()
        }
        vm.galleryAdapter.observe(requireActivity()) {
            recyclerView?.adapter = it
            recyclerView?.isVisible = vm.sliderImage.value!!.size >= 1
        }
    }
}