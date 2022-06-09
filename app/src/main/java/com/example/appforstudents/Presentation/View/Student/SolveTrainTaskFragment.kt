package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.appforstudents.Domain.ViewModel.Student.*
import com.example.appforstudents.R
import java.util.*
import kotlin.collections.ArrayList


class SolveTrainTaskFragment : Fragment() {

    private lateinit var vm: SolveTrainTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    var progressBar: ProgressBar? = null
    var counter = 100

    var task: TextView? = null
    var answersTextView: ArrayList<TextView?> = arrayListOf()
    var answer1: TextView? = null
    var answer2: TextView? = null
    var answer3: TextView? = null
    var answer4: TextView? = null
    var starsImageView: ArrayList<ImageView?> = arrayListOf()
    var star1: ImageView? = null
    var star2: ImageView? = null
    var star3: ImageView? = null
    var star4: ImageView? = null
    var star5: ImageView? = null
    var cases: ArrayList<RelativeLayout?> = arrayListOf()
    var case1: RelativeLayout? = null
    var case2: RelativeLayout? = null
    var case3: RelativeLayout? = null
    var case4: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), SolveTrainTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(SolveTrainTaskViewModel::class.java)
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
        task = view.findViewById(R.id.taskBody)
        answer1 = view.findViewById(R.id.taskAnswer1)
        answer2 = view.findViewById(R.id.taskAnswer2)
        answer3 = view.findViewById(R.id.taskAnswer3)
        answer4 = view.findViewById(R.id.taskAnswer4)
        answersTextView = arrayListOf(answer1, answer2, answer3, answer4)
        star1 = view.findViewById(R.id.view1Star)
        star2 = view.findViewById(R.id.view2Star)
        star3 = view.findViewById(R.id.view3Star)
        star4 = view.findViewById(R.id.view4Star)
        star5 = view.findViewById(R.id.view5Star)
        starsImageView = arrayListOf(star1, star2, star3, star4, star5)
        case1 = view.findViewById(R.id.answerCase1)
        case2 = view.findViewById(R.id.answerCase2)
        case3 = view.findViewById(R.id.answerCase3)
        case4 = view.findViewById(R.id.answerCase4)
        cases = arrayListOf(case1, case2, case3, case4)

        init()
    }

    private fun init(){
        val timer = Timer()
        val timerTask = object : TimerTask(){
            override fun run() {
                counter--
                progressBar?.progress = counter
                if (counter == 0){
                    if (vm.givedTask.value == false){
                        timer.cancel()
                        vm.giveTask()
                        vm.replace("GameTaskFragment", null)
                    }
                }
            }
        }
        timer.schedule(timerTask, 0, 200)

        vm.theme.observe(viewLifecycleOwner){
            if (it != null){
                vm.randomTask()
            }
        }
        vm.shuffled.observe(viewLifecycleOwner){
            if (it == true){
                task?.text = vm.firstNumber.value.toString() +" "+ vm.theme.value!!.symbol +" "+ vm.secondNumber.value + " = ?"

                for ((index,answer) in answersTextView.withIndex()){
                    answer?.text = vm.arrayListAnswers.value?.get(index).toString()
                }
                setCase()
            }
        }

        vm.getAnswers.observe(viewLifecycleOwner){
            if (it == 5){
                if (vm.givedTask.value == false){
                    timer.cancel()
                    vm.giveTask()
                    vm.replace("GameTaskFragment", null)
                }
            }
        }
    }

    private fun setCase(){
        for ((index, case) in cases.withIndex()) {
            case?.setOnClickListener {
                if (vm.arrayListAnswers.value?.get(index) == vm.answer.value) {
                    starsImageView[vm.getAnswers.value!!]?.setImageResource(R.drawable.ic_twotone_stars_24)
                    vm.rightAnswers.value = vm.rightAnswers.value!!.plus(1)
                } else {
                    starsImageView[vm.getAnswers.value!!]?.setImageResource(R.drawable.ic_twotone_stars_24_lose)
                }
                vm.getAnswers.value = vm.getAnswers.value!!.plus(1)
                vm.shuffled.value = false
                if (vm.getAnswers.value!! < 5) {
                    vm.randomTask()
                }
            }
        }
    }


}