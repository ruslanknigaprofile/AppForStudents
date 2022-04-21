package com.example.appforstudents.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R


class SolutionTestFragment : Fragment() {

    private lateinit var vm: ViewModel

    var recyclerView: RecyclerView? = null
    var testRecyclerView: RecyclerView? = null
    var taskBody: TextView? = null
    var giveSolution: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_solution_test, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {
        taskBody = view.findViewById(R.id.taskBody)
        taskBody?.text = vm.tasksList.value!!.get(vm.possitionSolutionTask.value!!).bodyTask


        giveSolution = view.findViewById(R.id.giveSolution)
        giveSolution?.setOnClickListener {
            val answer = arrayListOf<Boolean>()
            for (i in vm.testAdapter.value?.holders!!)
            {
                answer.add(i.checkBox.isChecked)
            }
            vm.updateCompletedTestTask(answer)
        }

        testRecyclerView = view.findViewById(R.id.testItemRecyclerView)
        testRecyclerView?.layoutManager = LinearLayoutManager(context)
        vm.setTestAdapter()
        vm.testAdapter.observe(requireActivity()){
            testRecyclerView?.adapter = it
        }

        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager

        if (vm.galleryAdapter.value == null) {
            vm.getImagesForSolution()
        }
        vm.galleryAdapter.observe(requireActivity()) {
            recyclerView?.adapter = it
            recyclerView?.isVisible = vm.sliderImage.value!!.size >= 1
        }
    }
}