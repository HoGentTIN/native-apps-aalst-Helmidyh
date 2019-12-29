package com.example.studymanager.ui.studiesessie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStudiesessieBinding
// import com.example.studymanager.studiesessie.StudieSessieFragmentArgs
import com.example.studymanager.studiesessie.StudieSessieViewModel

class StudieSessieFragment : Fragment() {

    private lateinit var binding: FragmentStudiesessieBinding
    private val viewModel: StudieSessieViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        ViewModelProviders.of(
            this, StudieSessieViewModel.Factory(
                StudieSessieFragmentArgs.fromBundle(arguments!!).taskId, activity.application
            )
        ).get(StudieSessieViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudiesessieBinding.inflate(inflater)

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


        viewModel.taskTimerFinished.observe(this, Observer { finished ->
            // task deleten of tijd toevoegen ?
        })
        return binding.root
    }

  override fun onStop() {
      super.onStop()
      binding.studiesessieViewModel?.persistTime()

  }
}