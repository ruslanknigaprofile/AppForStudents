package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appforstudents.Domain.ViewModel.Student.MainViewModelForStudent
import com.example.appforstudents.Domain.ViewModel.Student.SolutionTaskViewFactory
import com.example.appforstudents.Domain.ViewModel.Student.SolutionTaskViewModel
import com.example.appforstudents.R
class GalleryFragment : Fragment() {

    private lateinit var vm: SolutionTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), SolutionTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(SolutionTaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uriImage = arguments?.get("UriImage")
        Glide.with(requireActivity())
            .load(uriImage)
            .into(view.findViewById(R.id.provide_image))
    }

    override fun onStop() {
        if (vm.inGallery.value == true){
            vm.inGallery.value = null
        }
        super.onStop()
    }
}