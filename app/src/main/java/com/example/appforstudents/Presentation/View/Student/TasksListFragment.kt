package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewModel
import com.example.appforstudents.R


class TasksListFragment : Fragment() {

    private lateinit var vm: TasksListViewModel
    private lateinit var mainView: MainViewModelForStudent

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), TasksListViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(TasksListViewModel::class.java)

        vm.getCompletedTasks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks_list_for_studetn, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        //RecyclerView
        recyclerView = view.findViewById(R.id.tasksList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        vm.completedTasksList.observe(requireActivity()){
            vm.getTasksList()
        }
        vm.tasksListAdapter.observe(requireActivity()){
            recyclerView?.adapter = it
        }
    }
}