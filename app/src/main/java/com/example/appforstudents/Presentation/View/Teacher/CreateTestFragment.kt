package com.example.appforstudents.Presentation.View.Teacher

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.CreatTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Teacher.CreateTaskViewModel
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Model.Task
import com.example.appforstudents.R
import com.github.dhaval2404.imagepicker.ImagePicker


class CreateTestFragment : Fragment() {

    private lateinit var vm: CreateTaskViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var switch: Spinner? = null
    var taskBody: EditText? = null
    var addImage: ImageView? = null
    var recyclerViewGallery: RecyclerView? = null
    var addTestAnswer: ImageView? = null
    var recyclerViewTest: RecyclerView? = null
    var giveTest: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(requireActivity(), CreatTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(CreateTaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewGallery = view.findViewById(R.id.recyclerview_gallery)
        taskBody = view.findViewById(R.id.taskBody)
        addImage = view.findViewById(R.id.addImage)
        switch = view.findViewById(R.id.switchType)
        recyclerViewTest = view.findViewById(R.id.recyclerViewTest)
        addTestAnswer = view.findViewById(R.id.addTestAnswer)
        giveTest = view.findViewById(R.id.giveTest)

        dispose()
        init()
    }

    private fun init(){

        recyclerViewTest!!.layoutManager = LinearLayoutManager(context)
        vm.setTestAdapter()
        vm.testAdapter.observe(viewLifecycleOwner){
            recyclerViewTest!!.adapter = it
        }

        addTestAnswer!!.setOnClickListener {
            vm.addAnswerTest()
        }

        addImage!!.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        recyclerViewGallery?.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewGallery?.layoutManager = mLayoutManager
        recyclerViewGallery?.isVisible = false
        vm.galleryAdapter.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerViewGallery?.adapter = it
                recyclerViewGallery?.isVisible = it.itemCount >= 1
            }
        }

        giveTest?.setOnClickListener {
            checkView()
        }

        switch?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val position = resources.getStringArray(R.array.taskType)
                if (position.get(p2) == "Задача"){
                    vm.replace("CreateAnswerFragment",null)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun postTest(){
        vm.task.value?.bodyTask = taskBody?.text.toString()
        vm.task.value?.typeTask = "Test"
        vm.writeTaskInDB()
        vm.replace("TasksListFragment", null)
    }

    private fun checkView(){
        vm.saveDataTest()
        if (taskBody?.text.toString() == ""){
            mainView.createToast("Заполните поле вопроса!")
        }
        else if(!vm.task.value?.checkBoolean!!.contains("true")){
            mainView.createToast("Выберите хотя бы один правильный ответ!")
        }
        else if(vm.task.value?.listAnswers!!.contains("")){
            mainView.createToast("Заполните все поля вариантов ответа!")
        }
        else{
            mainView.createSimpleDialog(
                requireContext(),
                "Опубликовать задание?",
                "Если вы хотите опубликовать задание нажмите 'Да'.",
                { postTest() }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == ImagePicker.REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null){
            vm.task.value?.listImageUrl?.add(data.data.toString())
            vm.setGalleryAdapter()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun dispose(){
        vm.testAdapter.value = null
        vm.task.value?.listAnswers?.clear()
        vm.task.value?.checkBoolean?.clear()
        vm.task.value?.listImageUrl?.clear()
        vm.galleryAdapter.value = null
    }
}