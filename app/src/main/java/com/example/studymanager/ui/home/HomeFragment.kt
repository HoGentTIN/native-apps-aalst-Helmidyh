package com.example.studymanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentHomeBinding
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskAdapter
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskListener
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskLongClickListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: StudieTaskAdapter
    private val homeViewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        ViewModelProviders.of(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        binding = FragmentHomeBinding.inflate(inflater)
        binding.homeViewModel = homeViewModel
        binding.setLifecycleOwner(this)

        adapter = StudieTaskAdapter(StudieTaskListener { taskId ->
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToStudieSessieFragment(taskId))
        }, StudieTaskLongClickListener { taskId ->

            MaterialDialog(binding.root.context).show {
                title(text = "Wenst u de gekozen task te verwijderen ?").titleFont
                positiveButton(R.string.add, "Remove") {
                   homeViewModel.onStudieTaskLongClicked(taskId)
                    //deze mss nog aanpassen
                }
                negativeButton(R.string.cancel, "Cancel")
            }
        })

        binding.recyclerviewHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewHome.adapter = adapter
        //binding observes live data updates


        binding.btnAddTask.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_studieSessieCreatieFragment)
        )

        startListeners()

        return binding.root
    }

    private fun startListeners() {
        binding.homeViewModel?.tasks?.observe(this, Observer { tasks ->
            // a new version of the list is available !
            // detects changes and updates the list
            adapter.submitList(tasks)
        })
    }
}


