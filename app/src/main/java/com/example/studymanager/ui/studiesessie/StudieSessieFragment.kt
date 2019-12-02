package com.example.studymanager.ui.studiesessie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.R
import com.example.studymanager.database.StudieDatabase
import com.example.studymanager.databinding.FragmentStudiesessieBinding
import com.example.studymanager.domain.StudieTaskRepository
// import com.example.studymanager.studiesessie.StudieSessieFragmentArgs
import com.example.studymanager.studiesessie.StudieSessieViewModel
import com.example.studymanager.viewmodels.factories.StudieSessieViewModelFactory
import java.util.concurrent.TimeUnit

class StudieSessieFragment : Fragment() {
    private lateinit var binding: FragmentStudiesessieBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_studiesessie, container, false)

        val arguments = StudieSessieFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = StudieDatabase.getInstance(application).studieDatabaseDAO
        var repository = StudieTaskRepository(dataSource)
        var viewModelFactory = StudieSessieViewModelFactory(arguments.taskId, repository, application)
        var viewModel = ViewModelProvider(this, viewModelFactory)[StudieSessieViewModel::class.java]

        binding.studiesessieViewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnTaskTimerStart.setOnClickListener {

            when (binding.btnTaskTimerStart.text) {
                "Start" -> {
                    viewModel.onResume()
                    binding.btnTaskTimerStart.text = "Pauze"
                }
                "Pauze" -> {
                    viewModel.onPauze()
                    binding.btnTaskTimerStart.text = "Start"
                }
            }
        }

        binding.txtPlus5.setOnClickListener {
            viewModel.addTime("5")
        }

        binding.txtPlus10.setOnClickListener {
            viewModel.addTime("10")
        }

        binding.txtPlus15.setOnClickListener {
            viewModel.addTime("15")
        }

        //TODO Viewmodel binding met res file

        viewModel.taskTimerFinished.observe(this, Observer { finished ->
            // task deleten of tijd toevoegen ?
        })
        return binding.root
    }

}