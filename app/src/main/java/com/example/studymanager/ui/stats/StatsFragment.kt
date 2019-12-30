package com.example.studymanager.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.database.getDatabase
import com.example.studymanager.databinding.FragmentStatsBinding
import com.example.studymanager.models.domain.StatsRepository

class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private val viewModel: StatsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        val database = getDatabase(activity.application)
        val statsRepository = StatsRepository(database.statsDAO)
        ViewModelProviders.of(
            this, StatsViewModel.Factory(
            statsRepository
            )
        ).get(StatsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatsBinding.inflate(inflater)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}