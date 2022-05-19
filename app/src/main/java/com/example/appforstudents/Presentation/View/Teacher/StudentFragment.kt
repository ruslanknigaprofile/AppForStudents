package com.example.appforstudents.Presentation.View.Teacher

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.appforstudents.Domain.ViewModel.Teacher.*
import com.example.appforstudents.Model.Student
import com.example.appforstudents.R
import im.dacer.androidcharts.LineView


class StudentFragment : Fragment() {

    private lateinit var vm: StudentViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var lineView: LineView? = null
    var names: TextView? = null
    var rating: TextView? = null
    var completedTasks: TextView? = null
    var graphicView: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(requireActivity(), StudentViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(StudentViewModel::class.java)

        vm.getStudentById(arguments?.getString("studentId").toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        names = view.findViewById(R.id.studentNames)
        rating = view.findViewById(R.id.studentRaiting)
        completedTasks = view.findViewById(R.id.studentCompletedTasks)
        lineView = view.findViewById(R.id.lineView)
        graphicView = view.findViewById(R.id.graphicView)

        dispose()
        vm.student.observe(viewLifecycleOwner){
            if (it != null){
                init(it)
            }
        }
    }

    private fun init(student: Student){
        names?.text = student.name
        rating?.text = student.raiting.toString()
        completedTasks?.text = student.completedTask.size.toString()

        lineView?.setDrawDotLine(true)
        lineView?.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY)
        lineView?.setColorArray(intArrayOf(Color.GRAY))

        val strList = arrayListOf<String>()
        val dataList = java.util.ArrayList<java.util.ArrayList<Int>>()
        val line = arrayListOf<Int>()
        var asses: Int? = null

        for (task in student.completedTask){
            if (asses == null){
                asses = 0
            }
            if (task.asses){
                asses++
            }
            else{
                asses--
            }
            line.add(asses)
            strList.add(task.date)
        }

        if (asses != null){
            dataList.add(line)
        }
        if (strList.size > 0) {
            strList.reverse()
            dataList.reverse()
            lineView?.setBottomTextList(strList)
            lineView?.setDataList(dataList)
        }else {
            graphicView?.isVisible = false
        }
    }

    private fun dispose(){
        vm.student.value = null
    }
}