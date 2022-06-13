package com.example.appforstudents.Presentation.View.Student

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.SolveTrainTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.SolveTrainTaskViewModel
import com.example.appforstudents.R


class TheoryFragment : Fragment() {

    private lateinit var vm: SolveTrainTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    private var backImage: ImageView? = null
    private var nextImage: ImageView? = null
    private var image: ImageView? = null
    private var textSkip: TextView? = null
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), SolveTrainTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(SolveTrainTaskViewModel::class.java)

        vm.topicName.value = arguments?.getString("topic")
        vm.themeName.value = arguments?.getString("themes")
        vm.getThemes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_theory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image = view.findViewById(R.id.provide_image)
        backImage = view.findViewById(R.id.back)
        backImage?.isVisible = false
        nextImage = view.findViewById(R.id.next)
        nextImage?.isVisible = false
        textSkip = view.findViewById(R.id.skipped)

        dispose()
        init()
    }

    private fun init(){
        vm.theme.observe(viewLifecycleOwner){
            if (it != null){
                if (it.imageList.size >= 1){
                    setGallery()
                    nextImage?.isVisible = true
                }else{
                    vm.replace("SolveTrainTaskFragment", null)
                }
            }
        }

        backImage?.setOnClickListener {
            if (index >= 1){
                index--
                if (index == 0){
                    backImage?.isVisible = false
                }
                setGallery()
            }
        }
        nextImage?.setOnClickListener {
            if (index < vm.theme.value!!.imageList.size-1){
                index++
                backImage?.isVisible = true
                setGallery()
            }else{
                mainView.createSimpleDialog(
                    "Начать выполнение задания?",
                    "Нажав 'Да' вы перейдёте к выполнению задания."
                ) { vm.replace("SolveTrainTaskFragment", null) }
            }
        }

        textSkip?.setOnClickListener {
            mainView.createSimpleDialog(
                "Начать выполнение задания?",
                "Нажав 'Да' вы перейдёте к выполнению задания."
            ) { vm.replace("SolveTrainTaskFragment", null) }
        }
    }

    private fun setGallery(){
        Glide.with(requireActivity())
            .load(vm.theme.value!!.imageList[index].toUri())
            .centerInside()
            .into(image!!)
    }

    private fun dispose(){
        vm.shuffled.value = null
        vm.arrayListAnswers.value = null
        vm.theme.value = null
        vm.getAnswers.value = 0
        vm.rightAnswers.value = 0
        vm.givedTask.value = false
    }
}