package com.example.appforstudents.Presentation.Adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appforstudents.Domain.ViewModel.ViewModel
import com.example.appforstudents.R

class GalleryAdapter(val vm: ViewModel): RecyclerView.Adapter<GalleryAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.gallery_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryAdapter.TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        if (vm.sliderImage.value!!.size > position){
            val imageUri = vm.sliderImage.value!!.get(position)
            Glide.with(vm.getApplication<Application>().baseContext)
                .load(imageUri)
                .centerCrop()
                .into(holder.image)

            holder.image.setOnClickListener {
                vm.replace("GalleryFragment")
                vm.possitionGalleryItem.value = holder.position
            }
        }
    }

    override fun getItemCount(): Int {
        return vm.sliderImage.value?.size ?: 0
    }
}