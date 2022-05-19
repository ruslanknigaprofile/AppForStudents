package com.example.appforstudents.Presentation.View.Teacher

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
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Domain.ViewModel.Teacher.TasksListViewFactory
import com.example.appforstudents.Domain.ViewModel.Teacher.TasksListViewModel
import com.example.appforstudents.Presentation.Adapter.Teacher.TasksListAdapter
import com.example.appforstudents.R


class TasksListFragment : Fragment() {

    private lateinit var vm: TasksListViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var recyclerView: RecyclerView? = null
    var annotation: TextView? = null

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
        annotation = view.findViewById(R.id.annotation)
        recyclerView = view.findViewById(R.id.tasksList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        dispose()
        init()
    }

    private fun init(){
        vm.tasksListAdapter.observe(viewLifecycleOwner){
            if (it != null){
                recyclerView?.adapter = it
                if (it.itemCount > 0){
                    annotation?.isVisible = false
                    recyclerView?.isVisible = true
                }else{
                    annotation?.isVisible = true
                    recyclerView?.isVisible = false
                }
            }
        }

        vm.deleteTaskListener.value = object : TasksListAdapter.DeleteTaskListener{
            override fun deleteTask(id: String) {
                mainView.createSimpleDialog(requireContext(),
                    "Удалить задание",
                    "Вы действительно хотите удалить данное задание?",
                    { vm.deleteTask(id) })
            }
        }
    }

    private fun dispose(){
        vm.tasksListAdapter.value = null
    }
}