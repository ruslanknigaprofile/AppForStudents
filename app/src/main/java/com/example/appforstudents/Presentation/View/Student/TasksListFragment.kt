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
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.TasksListViewModel
import com.example.appforstudents.Model.Task
import com.example.appforstudents.Presentation.Adapter.Student.TasksListAdapter
import com.example.appforstudents.R


class TasksListFragment : Fragment() {

    private lateinit var vm: TasksListViewModel
    private lateinit var mainView: MainViewModelForStudent

    var recyclerView: RecyclerView? = null
    var annotation: TextView? = null

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
        return inflater.inflate(R.layout.fragment_tasks_list_for_studetn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.tasksList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        annotation = view.findViewById(R.id.annotation)

        dispose()
        init()
    }

    private fun init(){
        vm.completedTasksList.observe(viewLifecycleOwner){
            vm.getTasksList()
        }
        vm.tasksListAdapter.observe(viewLifecycleOwner){
            if (it != null){
                recyclerView?.adapter = it
                annotation?.isVisible = it.itemCount <= 0
            }
        }

        vm.startTaskListener.value = object : TasksListAdapter.StartTaskListener{
            override fun startTask(task: Task) {
                mainView.createSimpleDialog(requireContext(),
                    "Начать выполнение задания?",
                    "Если вы начнёте выполнение задания, вы не сможете прерваться, пока не завершите его.",
                    { vm.startTask(task) })
            }
        }
    }

    private fun dispose(){
        vm.tasksListAdapter.value = null
    }
}