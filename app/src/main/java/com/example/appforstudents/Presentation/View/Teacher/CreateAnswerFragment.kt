package com.example.appforstudents.Presentation.View.Teacher

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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Teacher.CreatTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Teacher.CreateTaskViewModel
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.Model.Task
import com.example.appforstudents.R
import com.github.dhaval2404.imagepicker.ImagePicker


class CreateAnswerFragment : Fragment() {

    private lateinit var vm: CreateTaskViewModel
    private lateinit var mainView: MainViewModelForTeacher

    var switch: Spinner? = null
    var taskBody: EditText? = null
    var addImage: ImageView? = null
    var recyclerViewGallery: RecyclerView? = null
    var editAnswer: EditText? = null
    var giveTest: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForTeacher::class.java)
        vm = ViewModelProvider(
            requireActivity(), CreatTaskViewFactory(
                requireActivity().application,
                mainView
            )
        ).get(CreateTaskViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewGallery = view.findViewById(R.id.recyclerview_gallery)
        taskBody = view.findViewById(R.id.taskBody)
        addImage = view.findViewById(R.id.addImage)
        switch = view.findViewById(R.id.switchType)
        editAnswer = view.findViewById(R.id.editAnswer)
        giveTest = view.findViewById(R.id.giveTest)

        dispose()
        init()
    }

    private fun init() {
        addImage!!.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        recyclerViewGallery?.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewGallery?.layoutManager = mLayoutManager
        recyclerViewGallery?.isVisible = false
        vm.galleryAdapter.observe(viewLifecycleOwner) {
            if(it != null) {
                recyclerViewGallery?.adapter = it
                recyclerViewGallery?.isVisible = it.itemCount >= 1
            }
        }

        giveTest?.setOnClickListener {
            checkView()
        }

        switch?.setSelection(1)
        switch?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val position = resources.getStringArray(R.array.taskType)
                if (position.get(p2) == "Тест") {
                    vm.replace("CreateTestFragment", null)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun postTest() {
        vm.task.value?.listAnswers?.add(editAnswer?.text.toString())
        vm.task.value?.checkBoolean?.add(true.toString())
        vm.task.value?.bodyTask = taskBody?.text.toString()
        vm.task.value?.typeTask = "Answer"
        vm.writeTaskInDB()
        vm.replace("TasksListFragment", null)
    }

    private fun checkView(){
        if(taskBody?.text.toString() == ""){
            mainView.createToast("Заполните поле вопроса!")
        }
        else if(editAnswer?.text.toString() == ""){
            mainView.createToast("Заполните поле ответа!")
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
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            vm.task.value?.listImageUrl?.add(data.data.toString())
            vm.setGalleryAdapter()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun dispose(){
        vm.task.value?.listAnswers?.clear()
        vm.task.value?.checkBoolean?.clear()
        vm.task.value?.listImageUrl?.clear()
        vm.galleryAdapter.value = null
    }
}