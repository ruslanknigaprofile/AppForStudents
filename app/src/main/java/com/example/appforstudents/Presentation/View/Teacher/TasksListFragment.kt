package com.example.appforstudents.Presentation.View.Teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Domain.ViewModel.Teacher.TasksListViewFactory
import com.example.appforstudents.Domain.ViewModel.Teacher.TasksListViewModel
import com.example.appforstudents.R


class TasksListFragment : Fragment() {

    private lateinit var vm: TasksListViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(requireActivity(), TasksListViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(TasksListViewModel::class.java)

        vm.getTasksList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks_list_for_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView
        recyclerView = view.findViewById(R.id.tasksList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        init()
    }

    private fun init(){
        vm.tasksListAdapter.observe(requireActivity()){
            recyclerView?.adapter = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.tasksListAdapter.removeObservers(requireActivity())
        vm.tasksListAdapter.value = null
    }
}