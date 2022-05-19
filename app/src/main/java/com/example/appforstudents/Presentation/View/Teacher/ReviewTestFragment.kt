package com.example.appforstudents.Presentation.View.Teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.*
import com.example.appforstudents.R


class ReviewTestFragment : Fragment() {

    private lateinit var vm: ReviewTaskViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var taskBody: TextView? = null
    var testList: RecyclerView? = null
    var recyclerViewGallery: RecyclerView? = null
    var studentsList: RecyclerView? = null
    var imageView: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(requireActivity(), ReviewTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(ReviewTaskViewModel::class.java)

        if(arguments?.getString("taskId") == null){
            vm.getTaskById(vm.task.value?.id!!)
            vm.getStudentsMadeTask(vm.task.value?.id!!)
        }else{
            vm.getTaskById(arguments?.getString("taskId").toString())
            vm.getStudentsMadeTask(arguments?.getString("taskId").toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskBody = view.findViewById(R.id.taskBody)
        testList = view.findViewById(R.id.testList)
        recyclerViewGallery = view.findViewById(R.id.recyclerViewGallery)
        studentsList = view.findViewById(R.id.studentsList)
        imageView = view.findViewById(R.id.imageView)


        dispose()
        init()
    }

    private fun init(){
        vm.task.observe(viewLifecycleOwner) {
            if (it != null) {
                taskBody?.text = it.bodyTask

                vm.setTestAdapter()
                //vm.setSliderAdapter()
                vm.getImages()
            }
        }

        testList!!.layoutManager = LinearLayoutManager(context)
        vm.testAdapter.observe(viewLifecycleOwner) {
            if (it != null) {
                testList?.adapter = it
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
        vm.studentsAdapter.observe(requireActivity()){
            studentsList?.adapter = it
        }
    }

    private fun dispose(){
        //vm.task.removeObservers(requireActivity())
        vm.task.value = null
        //vm.testAdapter.removeObservers(requireActivity())
        vm.testAdapter.value = null
        //vm.galleryAdapter.removeObservers(requireActivity())
        vm.galleryAdapter.value = null
    }
}