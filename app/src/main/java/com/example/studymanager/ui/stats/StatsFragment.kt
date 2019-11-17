package com.example.studymanager.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_stats, container, false)

        return binding.root
    }

}