package com.example.appforstudents.Presentation.View.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforstudents.Domain.ViewModel.Student.*
import com.example.appforstudents.R

class GameTaskFragment : Fragment() {

    private lateinit var vm: GameTaskViewModel
    private lateinit var mainView: MainViewModelForStudent

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ViewModelProvider(requireActivity()).get(MainViewModelForStudent::class.java)
        vm = ViewModelProvider(requireActivity(), GameTaskViewFactory(
            requireActivity().application,
            mainView
        )
        ).get(GameTaskViewModel::class.java)

        vm.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        dispose()
        init()
    }

    private fun init(){
        vm.topicList.observe(viewLifecycleOwner){
            if (it != null && vm.student.value?.completedTopic != null){
                vm.setGameTaskAdapter()
            }
        }

        vm.tasksListAdapter.observe(viewLifecycleOwner){
            if (it != null){
                recyclerView?.adapter = it
            }
        }
    }

    private fun dispose(){
        vm.tasksListAdapter.value = null
    }
}