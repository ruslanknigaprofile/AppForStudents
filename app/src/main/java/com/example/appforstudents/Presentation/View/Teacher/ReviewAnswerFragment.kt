package com.example.appforstudents.Presentation.View.Teacher

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
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Domain.ViewModel.Teacher.ReviewTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Teacher.ReviewTaskViewModel
import com.example.appforstudents.Presentation.Adapter.Teacher.StudentAssesAdapter
import com.example.appforstudents.R


class ReviewAnswerFragment : Fragment() {

    private lateinit var vm: ReviewTaskViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var taskBody: TextView? = null
    var rightAnswer: TextView? = null
    var recyclerViewGallery: RecyclerView? = null
    var studentsList: RecyclerView? = null
    var imageView: LinearLayout? = null
    var amountStudents: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(requireActivity(), ReviewTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(ReviewTaskViewModel::class.java)

        if(arguments?.getString("taskId") != null) {
            dispose()
            vm.getTaskById(arguments?.getString("taskId").toString())
            vm.getStudentsMadeTask(arguments?.getString("taskId").toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskBody = view.findViewById(R.id.taskBody)
        rightAnswer = view.findViewById(R.id.rightAnswer)
        recyclerViewGallery = view.findViewById(R.id.recyclerViewGallery)
        studentsList = view.findViewById(R.id.studentsList)
        imageView = view.findViewById(R.id.imageView)
        amountStudents = view.findViewById(R.id.amountStudents)

        init()
    }

    private fun init(){
        vm.task.observe(viewLifecycleOwner) {
            if (it != null) {
                taskBody?.text = it.bodyTask
                rightAnswer?.text = "Ответ: " + it.listAnswers.get(0)
                if (vm.testAdapter.value == null){
                    vm.setTestAdapter()
                }
                val qwe = vm.galleryAdapter.value
                if(vm.galleryAdapter.value == null){
                    vm.getImages()
                }
            }
        }

        recyclerViewGallery?.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewGallery?.layoutManager = mLayoutManager
        imageView?.isVisible = false
        vm.imagesList.observe(viewLifecycleOwner){
            if (it.size > 0){
                vm.setSliderAdapter()
            }
        }
        vm.galleryAdapter.observe(viewLifecycleOwner){
            if(it != null){
                recyclerViewGallery?.adapter = it
                imageView?.isVisible = it.itemCount >= 1
            }
        }

        studentsList?.layoutManager = LinearLayoutManager(context)
        vm.studentsAdapter.observe(viewLifecycleOwner){
            studentsList?.adapter = it
            amountStudents?.text = "Количество учащихся выполнившие тест: " + it.itemCount + "/" + vm.amountStudents.value
        }

        vm.studentChangeAssesListener.value = object : StudentAssesAdapter.ChangeAsses{
            override fun changeAsses(studentId: String, taskId: String) {
                mainView.createSimpleDialog(requireContext(),
                    "Изменить оценивание",
                    "Вы уверены что хотите изменить оценку данного учащегося?",
                    { vm.changeAssesInDB(studentId, taskId) })
            }
        }
    }

    private fun dispose(){
        vm.task.value = null
        vm.imagesList.value?.clear()
        vm.galleryAdapter.value = null
        vm.studentChangeAssesListener.value = null
    }
}