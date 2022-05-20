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
import com.example.appforstudents.Domain.ViewModel.Teacher.*
import com.example.appforstudents.R


class StudentsListFragment : Fragment() {

    private lateinit var vm: StudentsListViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var recyclerView: RecyclerView? = null
    var annotation: TextView? = null

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
        annotation = view.findViewById(R.id.annotation)
        recyclerView = view.findViewById(R.id.studentsList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        dispose()
        init()
    }

    private fun init(){
        vm.studentsListAdapter.observe(viewLifecycleOwner){
            if(it != null){
                recyclerView?.adapter = it
                annotation?.isVisible = it.itemCount <= 0
            }
        }
    }

    private fun dispose(){
        vm.studentsListAdapter.value = null
    }
}