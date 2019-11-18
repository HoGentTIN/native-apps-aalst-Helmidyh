package com.example.studymanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentHomeBinding
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

        val adapter = HomeTaskAdapter()
        binding.recyclerviewHome.adapter = adapter

        binding.btnAddTask.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_studieSessieCreatieFragment)
        )

        return binding.root
    }


}