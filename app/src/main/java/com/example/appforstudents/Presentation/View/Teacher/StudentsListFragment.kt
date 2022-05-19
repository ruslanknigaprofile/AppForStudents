package com.example.appforstudents.Presentation.View.Teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.*
import com.example.appforstudents.R


class StudentsListFragment : Fragment() {

    private lateinit var vm: StudentsListViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(requireActivity(), StudentsListViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(StudentsListViewModel::class.java)

        vm.getStudents()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_students_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.studentsList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        dispose()
        init()
    }

    private fun init(){
        vm.studentsListAdapter.observe(requireActivity()){
            recyclerView?.adapter = it
        }
    }

    private fun dispose(){
        vm.studentsListAdapter.removeObservers(requireActivity())
        vm.studentsListAdapter.value = null
    }
}