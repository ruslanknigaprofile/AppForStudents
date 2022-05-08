package com.example.appforstudents.Presentation.View.Student

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
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.ReviewCompletedTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.ReviewCompletedTaskViewModel
import com.example.appforstudents.R


class ReviewCompletedTaskFragment : Fragment() {

    private lateinit var vm: ReviewCompletedTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    var linearLayout: LinearLayout? = null
    var taskBody: TextView? = null
    var rightAnswer: TextView? = null
    var giveAnswer: TextView? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), ReviewCompletedTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(ReviewCompletedTaskViewModel::class.java)

        if (arguments?.getString("positionReviewCompletedTask") != "position"){
            val pos =  arguments?.getString("positionReviewCompletedTask")
            vm.position.value = pos?.toInt()
            vm.galleryAdapter.value = null
        }

        vm.getCompletedTasks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review_completed_task, container, false)

        init(view)

        return view
    }

    private fun init(view: View)
    {
        val possition = vm.position.value!!

        linearLayout = view.findViewById(R.id.background_image_view)

        taskBody = view.findViewById(R.id.taskBody)
        rightAnswer = view.findViewById(R.id.rightAnswer)
        giveAnswer = view.findViewById(R.id.giveAnswer)

        vm.completedTasksList.observe(requireActivity())
        {
            if (it != null) {
                if (vm.galleryAdapter.value == null){
                    vm.getImagesForReview()
                }

                val completedTask = vm.completedTasksList.value!!.get(possition)
                if (completedTask.asses) {
                    linearLayout?.setBackgroundColor(getResources().getColor(R.color.asses))
                } else {
                    linearLayout?.setBackgroundColor(getResources().getColor(R.color.dont_asses))
                }

                taskBody?.text = completedTask.task.bodyTask

                if (completedTask.task.typeTask == "Test") {
                    var answer = ""
                    for ((index, i) in completedTask.task.checkBoolean.withIndex()) {
                        if (i.toBoolean()) {
                            answer += completedTask.task.listAnswers.get(index) + "."
                        }
                    }
                    rightAnswer?.text = answer
                } else {
                    rightAnswer?.text = completedTask.task.listAnswers.get(0) + "."
                }
                giveAnswer?.text = completedTask.answer
            }
        }


        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.isVisible = false


        vm.galleryAdapter.observe(requireActivity()) {
            if (it != null) {
                recyclerView?.adapter = it
                recyclerView?.isVisible = vm.sliderImage.value!!.size >= 1
            }
        }
    }

    override fun onDestroyView() {
        vm.galleryAdapter.removeObservers(requireActivity())
        vm.completedTasksList.removeObservers(requireActivity())
        vm.completedTasksList.value = null
        //vm.galleryAdapter.value = null

        super.onDestroyView()
    }
}