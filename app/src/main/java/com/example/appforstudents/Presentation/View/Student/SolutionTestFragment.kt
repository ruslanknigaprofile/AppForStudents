package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.SolutionTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.SolutionTaskViewModel
import com.example.appforstudents.R


class SolutionTestFragment : Fragment() {

    private lateinit var vm: SolutionTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    var recyclerView: RecyclerView? = null
    var testRecyclerView: RecyclerView? = null
    var taskBody: TextView? = null
    var giveSolution: AppCompatButton? = null
    var galleryView: LinearLayout? = null
    var teacherString: TextView? = null
    var taskDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), SolutionTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(SolutionTaskViewModel::class.java)

        if (arguments?.getString("positionSolutionTestTask") != "id"){
            dispose()
            vm.taskId.value = arguments?.getString("positionSolutionTestTask")
            vm.getData()
            vm.getTask()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_solution_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryView = view.findViewById(R.id.galleryView)
        taskBody = view.findViewById(R.id.taskBody)
        giveSolution = view.findViewById(R.id.giveSolution)
        testRecyclerView = view.findViewById(R.id.testItemRecyclerView)
        testRecyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        teacherString = view.findViewById(R.id.teacher_string)
        taskDate = view.findViewById(R.id.dateTask)

        init()
    }

    private fun init(){
        vm.task.observe(viewLifecycleOwner){
            if (it != null){
                if (vm.galleryAdapter.value == null){
                    vm.getImagesForReview()
                }

                taskBody?.text = it.bodyTask
                vm.setTestAdapter()
                teacherString?.text = it.teacherNames
                taskDate?.text = it.date + " в " + it.time
            }
        }
        vm.imagesList.observe(viewLifecycleOwner){
            if (it != null){
                vm.setSliderAdapter()
            }
        }
        vm.testAdapter.observe(viewLifecycleOwner){
            if (vm.task.value != null) {
                testRecyclerView?.adapter = it
            }
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = mLayoutManager
        galleryView?.isVisible = false
        vm.galleryAdapter.observe(viewLifecycleOwner) {
            if(it != null) {
                recyclerView?.adapter = it
                galleryView?.isVisible = it.itemCount >= 1
            }
        }

        giveSolution?.setOnClickListener {
            mainView.createSimpleDialog("Завершить задание?",
            "Нажмите 'Да' если хотите дать ответ на задние и получить результат."
            ) {
                getAnswer()
                vm.replace("CompletedTasksListFragment", null)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (vm.inGallery.value != true){
            getAnswer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (vm.endTask.value == true){
            vm.replace("CompletedTasksListFragment", null)
        }
    }

    private fun getAnswer(){
        if (vm.endTask.value != true){
            val answer = arrayListOf<Boolean>()
            if (vm.testAdapter.value?.holders != null){
                for (i in vm.testAdapter.value?.holders!!)
                {
                    answer.add(i.checkBox.isChecked)
                }
                vm.updateCompletedTestTask(answer)
            }
        }
    }

    private fun dispose(){
        vm.task.value = null
        vm.testAdapter.value = null
        vm.galleryAdapter.value = null
        vm.endTask.value = null
        vm.inGallery.value = null
    }
}