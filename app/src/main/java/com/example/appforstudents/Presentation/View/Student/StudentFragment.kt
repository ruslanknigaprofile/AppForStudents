package com.example.appforstudents.Presentation.View.Student

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
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.StudentViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.StudentViewModel
import com.example.appforstudents.Model.Student
import com.example.appforstudents.R
import im.dacer.androidcharts.BarView
import im.dacer.androidcharts.LineView


class StudentFragment : Fragment() {

    private lateinit var vm: StudentViewModel
    private lateinit var mainView: MainViewModelForStudent

    var lineView: LineView? = null
    var barView: BarView? = null
    var names: TextView? = null
    var rating: TextView? = null
    var completedTasks: TextView? = null
    var graphicView: RelativeLayout? = null
    var diagramView: RelativeLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), StudentViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(StudentViewModel::class.java)

        vm.getStudentById()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        names = view.findViewById(R.id.studentNames)
        rating = view.findViewById(R.id.studentRaiting)
        completedTasks = view.findViewById(R.id.studentCompletedTasks)
        lineView = view.findViewById(R.id.lineView)
        barView = view.findViewById(R.id.circleView)
        graphicView = view.findViewById(R.id.graphicView)
        diagramView = view.findViewById(R.id.diagramView)

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

        //setLineView
        lineView?.setDrawDotLine(true)
        lineView?.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY)
        lineView?.setColorArray(intArrayOf(Color.GRAY))

        val strListLine = arrayListOf<String>()
        val dataListLine = java.util.ArrayList<java.util.ArrayList<Int>>()
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
            strListLine.add(task.date)
        }

        if (asses != null){
            dataListLine.add(line)
        }
        if (strListLine.size > 0) {
            strListLine.reverse()
            dataListLine.reverse()
            lineView?.setBottomTextList(strListLine)
            lineView?.setDataList(dataListLine)
        }else {
            graphicView?.isVisible = false
        }

        //setBarView
        val dataBar = arrayListOf<Int>()
        val strListBar = arrayListOf<String>()
        for (topic in student.completedTopic){
            strListBar.add(topic.topic.name)
            dataBar.add(topic.topic.themes.size*25)
        }
        if (strListBar.size > 0){
            barView?.setBottomTextList(strListBar);
            barView?.setDataList(dataBar,100);
        }else{
            diagramView?.isVisible = false
        }
    }

    private fun dispose(){
        vm.student.value = null
    }
}