package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
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

    var scroll: ScrollView? = null
    var taskBody: TextView? = null
    var rightAnswer: TextView? = null
    var giveAnswer: TextView? = null
    var recyclerView: RecyclerView? = null
    var galleryView: LinearLayout? = null
    var teacherNames: TextView? = null
    var dateTask: TextView? = null
    var dateAnswer: TextView? = null
    var timeTask: TextView? = null
    var timeAnswer: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), ReviewCompletedTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(ReviewCompletedTaskViewModel::class.java)

        if (arguments?.getString("positionReviewCompletedTask") != "position"){
            dispose()
            val pos =  arguments?.getString("positionReviewCompletedTask")
            vm.position.value = pos?.toInt()
            vm.getCompletedTasks()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review_completed_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scroll = view.findViewById(R.id.viewId)
        taskBody = view.findViewById(R.id.taskBody)
        rightAnswer = view.findViewById(R.id.rightAnswer)
        giveAnswer = view.findViewById(R.id.giveAnswer)
        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        galleryView = view.findViewById(R.id.galleryView)
        teacherNames = view.findViewById(R.id.teacher_string)
        dateTask = view.findViewById(R.id.dateTask)
        dateAnswer = view.findViewById(R.id.dateAnswer)
        timeTask = view.findViewById(R.id.timeTask)
        timeAnswer = view.findViewById(R.id.timeAnswer)

        init()
    }

    private fun init()
    {
        val possition = vm.position.value!!

        vm.completedTasksList.observe(viewLifecycleOwner)
        {
            if (it != null) {
                if (vm.galleryAdapter.value == null){
                    vm.getImagesForReview()
                }

                val completedTask = vm.completedTasksList.value!!.get(possition)

                if (completedTask.asses) {
                    scroll?.setBackgroundColor(getResources().getColor(R.color.asses))
                } else {
                    scroll?.setBackgroundColor(getResources().getColor(R.color.dont_asses))
                }
                teacherNames?.text = completedTask.task.teacherNames
                dateTask?.text = completedTask.task.date
                timeTask?.text = "в " + completedTask.task.time
                dateAnswer?.text = completedTask.date
                timeAnswer?.text = "в " + completedTask.time
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

        vm.imagesList.observe(viewLifecycleOwner){
            if (it != null){
                vm.setSliderAdapter()
            }
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager
        galleryView?.isVisible = false

        vm.galleryAdapter.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerView?.adapter = it
                galleryView?.isVisible = it.itemCount > 0
            }
        }
    }

    private fun dispose(){
        vm.completedTasksList.value = null
        vm.galleryAdapter.value = null
        vm.imagesList.value = null
        vm.position.value = null
    }
}