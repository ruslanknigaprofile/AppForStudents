package com.example.appforstudents.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R

class CompletedTasksListFragment : Fragment() {

    private lateinit var vm: ViewModel

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
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

        vm.setCompletedTasksListAdapter()
        recyclerView?.adapter = vm.completedTasksListAdapter.value


    }
}