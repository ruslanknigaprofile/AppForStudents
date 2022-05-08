package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val view = inflater.inflate(R.layout.fragment_completed_tasks_list, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {
        //RecyclerView
        recyclerView = view.findViewById(R.id.completedTasksList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        vm.completedTasksListAdapter.observe(requireActivity()){
            recyclerView!!.adapter = it
        }
        vm.completedTasksList.observe(requireActivity()){
            vm.setCompletedTasksListAdapter()
        }
    }
}