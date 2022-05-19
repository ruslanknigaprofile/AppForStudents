package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.SolutionTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.SolutionTaskViewModel
import com.example.appforstudents.R


class SolutionAnswerTaskFragment : Fragment() {

    private lateinit var vm: SolutionTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    var recyclerView: RecyclerView? = null
    var taskBody: TextView? = null
    var editAnswer: EditText? = null
    var giveSolution: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), SolutionTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(SolutionTaskViewModel::class.java)

        if (arguments?.getString("positionSolutionAnswerTask") != "id"){
            vm.taskId.value = arguments?.getString("positionSolutionAnswerTask")
            vm.galleryAdapter.value = null
        }
        vm.getData()
        vm.getTask()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_solution_answer_task, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        taskBody = view.findViewById(R.id.taskBody)
        giveSolution = view.findViewById(R.id.giveSolution)
        editAnswer = view.findViewById(R.id.editAnswer)

        vm.task.observe(requireActivity()){
            if(it != null) {
                if (vm.galleryAdapter.value == null){
                    //vm.getImagesForReview()
                    vm.setSliderAdapter()
                }
                taskBody?.text = vm.task.value?.bodyTask
            }
        }



        giveSolution?.setOnClickListener {
            vm.updateCompletedAnswerTask(editAnswer?.text.toString())
        }

        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.isVisible = false

        vm.galleryAdapter.observe(requireActivity()){
            if(it != null) {
                recyclerView?.adapter = it
                recyclerView?.isVisible = it.itemCount >= 1
            }
        }
    }

    override fun onDestroyView() {
        vm.task.removeObservers(requireActivity())
        vm.testAdapter.removeObservers(requireActivity())
        vm.galleryAdapter.removeObservers(requireActivity())
        vm.task.value = null
        vm.testAdapter.value = null
        //vm.galleryAdapter.value = null
        super.onDestroyView()
    }
}