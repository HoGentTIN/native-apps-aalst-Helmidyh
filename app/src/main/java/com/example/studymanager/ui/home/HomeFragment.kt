package com.example.studymanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentHomeBinding
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskAdapter
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskListener

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

        adapter = StudieTaskAdapter(StudieTaskListener { taskId ->
            homeViewModel.onStudieTaskClicked(taskId)
        })

        binding = FragmentHomeBinding.inflate(inflater)
        binding.homeViewModel = homeViewModel
        binding.recyclerviewHome.adapter = adapter
        //binding observes live data updates
        binding.setLifecycleOwner(this)

        startListeners()

        homeViewModel.navigateToStudiesessie.observe(this, Observer { studieTask ->
            studieTask?.let {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToStudieSessieFragment(studieTask))
            }
        })

        binding.btnAddTask.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_studieSessieCreatieFragment)
        )

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


