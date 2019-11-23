package com.example.studymanager.studiesessie

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStudiesessieBinding
import com.example.studymanager.ui.studiesessie.viewmodels.StudieSessieViewModelFactory

class StudieSessieFragment : Fragment() {

    private lateinit var binding: FragmentStudiesessieBinding
    private lateinit var viewModel: StudieSessieViewModel
    private lateinit var viewModelFactory: StudieSessieViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_studiesessie, container, false)
        val x =
            StudieSessieFragmentArgs.fromBundle(arguments!!)
        viewModelFactory =
            StudieSessieViewModelFactory(
                x.taskId
            )
        viewModel = ViewModelProvider(this, viewModelFactory)[StudieSessieViewModel::class.java]

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
        binding.txtTaskTitel.text = viewModel.taskTitle

        binding.txtPlus5.setOnClickListener {
            viewModel.addTime("5")
        }
        binding.txtPlus10.setOnClickListener {
            viewModel.addTime("10")
        }
        binding.txtPlus15.setOnClickListener {
            viewModel.addTime("15")
        }
        viewModel.currentTime.observe(
            this,
            Observer { newTime ->
                binding.txtTaskTimer.text = DateUtils.formatElapsedTime(newTime)
            })
        viewModel.timerFinished.observe(this,Observer{ finished ->

            // task deleten of tijd toevoegen ?

        })

        return binding.root
    }

}