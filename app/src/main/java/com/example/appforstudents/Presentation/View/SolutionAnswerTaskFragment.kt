package com.example.appforstudents.Presentation.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R


class SolutionAnswerTaskFragment : Fragment() {

    private lateinit var vm: ViewModel

    var recyclerView: RecyclerView? = null
    var taskBody: TextView? = null
    var editAnswer: EditText? = null
    var giveSolution: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
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
        taskBody?.text = vm.tasksList.value!!.get(vm.possitionSolutionTask.value!!).bodyTask

        editAnswer = view.findViewById(R.id.editAnswer)

        giveSolution = view.findViewById(R.id.giveSolution)
        giveSolution?.setOnClickListener {
            vm.updateCompletedAnswerTask(editAnswer?.text.toString())
        }

        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager

        if (vm.galleryAdapter.value == null){
            vm.getImagesForSolution()
        }
        vm.galleryAdapter.observe(requireActivity()){
            recyclerView?.adapter = it
            recyclerView?.isVisible = vm.sliderImage.value!!.size >= 1
        }
    }
}