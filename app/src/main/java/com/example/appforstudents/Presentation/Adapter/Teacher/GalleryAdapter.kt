package com.example.appforstudents.Presentation.Adapter.Teacher

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appforstudents.Domain.ViewModel.Teacher.MainViewModelForTeacher
import com.example.appforstudents.R

class GalleryAdapter(val sliderImage: ArrayList<Uri>, val context: Context, val vm: MainViewModelForTeacher): RecyclerView.Adapter<GalleryAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.gallery_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {

        if (sliderImage.size > position){
            val imageUri = sliderImage.get(position)
            Glide.with(context)
                .load(imageUri)
                .centerCrop()
                .into(holder.image)

            holder.image.setOnClickListener {
                val bundle =  Bundle()
                bundle.putString("UriImage", imageUri.toString())
                vm.replace("GalleryFragment", bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }
}