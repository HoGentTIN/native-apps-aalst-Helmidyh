package com.example.studymanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = StudieDatabase.getInstance(application).studieDatabaseDAO

        val viewModelFactory = HomeViewModelFactory(dataSource,application)

        val homeViewModel = ViewModelProviders.of(this,viewModelFactory).get(HomeViewModel::class.java)
        //binding observes live data updates
        binding.setLifecycleOwner(this)

        val adapter = HomeTaskAdapter()
        binding.recyclerviewHome.adapter = adapter

        binding.btnAddTask.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_studieSessieCreatieFragment)
        )

        return binding.root
    }


}