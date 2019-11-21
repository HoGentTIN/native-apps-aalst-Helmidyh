package com.example.studymanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.studymanager.R
import com.example.studymanager.database.StudieDatabase
import com.example.studymanager.databinding.FragmentHomeBinding
import com.example.studymanager.ui.home.HomeViewModelFactory
import com.example.studymanager.ui.home.adapters.HomeTaskAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeTaskAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val dataSource = StudieDatabase.getInstance(application).studieDatabaseDAO
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        adapter = HomeTaskAdapter()
        binding.homeViewModel = homeViewModel
        binding.recyclerviewHome.adapter = adapter
        //binding van adapter data list aan viewmodel data list

        startListeners()

        //binding observes live data updates
        binding.setLifecycleOwner(this)
        binding.btnAddTask.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_studieSessieCreatieFragment)
        )
        /// oberserver aan button toevoegen voor te testen (normaal moet het aan de create knop in studiecreatiefragment komen)
        return binding.root
    }


    private fun startListeners() {
        homeViewModel?.tasks.observe(this, Observer { tasks ->
            // a new version of the list is available !
            // detects changes and updates the list
            adapter.submitList(tasks)
        })
    }
}


