package com.example.appforstudents.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R


class GalleryFragment : Fragment() {

    private lateinit var vm: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        Glide.with(requireActivity())
            .load(vm.sliderImage.value!!.get(vm.possitionGalleryItem.value!!))
            .into(view.findViewById(R.id.provide_image))
    }
}