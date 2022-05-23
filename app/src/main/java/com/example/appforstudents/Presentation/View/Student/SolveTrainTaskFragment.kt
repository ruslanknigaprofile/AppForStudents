package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.loader.content.AsyncTaskLoader
import com.example.appforstudents.R
import java.util.*


class SolveTrainTaskFragment : Fragment() {

    var progressBar: ProgressBar? = null
    var counter = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_solve_train_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)

        dispose()
        init()
    }

    private fun init(){
        val timer = Timer()
        val timerTask = object : TimerTask(){
            override fun run() {
                counter--
                progressBar?.progress = counter
                if (counter == 0){
                    timer.cancel()
                }
            }
        }
        timer.schedule(timerTask, 0, 300)
    }

    private fun dispose(){

    }
}