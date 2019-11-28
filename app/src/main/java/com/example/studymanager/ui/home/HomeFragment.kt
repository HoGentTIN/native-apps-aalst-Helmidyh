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
import com.example.studymanager.database.StudieDatabase
import com.example.studymanager.databinding.FragmentHomeBinding
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.viewmodels.factories.HomeViewModelFactory
import com.example.studymanager.viewmodels.adapters.adapters.HomeTaskAdapter
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeTaskAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = StudieDatabase.getInstance(application).studieDatabaseDAO
        val repository = StudieTaskRepository(dataSource)
        val viewModelFactory = HomeViewModelFactory(repository, application)

        val homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        adapter = HomeTaskAdapter(StudieTaskListener { taskId ->
            homeViewModel.onStudieTaskClicked(taskId)
        })

        binding.homeViewModel = homeViewModel
        binding.recyclerviewHome.adapter = adapter

        homeViewModel.navigateToStudiesessie.observe(this, Observer { studieTask ->
            studieTask?.let {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToStudieSessieFragment(studieTask))
            }
        })

        startListeners()

        //binding observes live data updates
        binding.setLifecycleOwner(this)

        //deze clicklistener moet naar nieuw studie creatie fragment gaan
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


