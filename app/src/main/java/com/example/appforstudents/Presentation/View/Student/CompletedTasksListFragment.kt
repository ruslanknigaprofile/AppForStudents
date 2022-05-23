package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.CompletedTasksListViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.CompletedTasksListViewModel
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.R

class CompletedTasksListFragment : Fragment() {

    private lateinit var vm: CompletedTasksListViewModel
    private lateinit var mainView: MainViewModelForStudent

    var recyclerView: RecyclerView? = null
    var annotation: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), CompletedTasksListViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(CompletedTasksListViewModel::class.java)

        vm.getCompletedTasks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_completed_tasks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        annotation = view.findViewById(R.id.annotation)
        recyclerView = view.findViewById(R.id.completedTasksList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        dispose()
        init()
    }

    private fun init() {
        vm.completedTasksListAdapter.observe(viewLifecycleOwner){
            if (it != null){
                annotation?.isVisible = it.itemCount <= 0
                recyclerView!!.adapter = it
            }
        }
        vm.completedTasksList.observe(viewLifecycleOwner){
            if (it != null){
                vm.setCompletedTasksListAdapter()
            }
        }
    }

    private fun dispose(){
        vm.completedTasksList.value = null
        vm.completedTasksListAdapter.value = null
    }
}