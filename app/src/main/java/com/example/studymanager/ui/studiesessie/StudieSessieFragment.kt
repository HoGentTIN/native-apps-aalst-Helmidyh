package com.example.studymanager.ui.studiesessie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.database.getDatabase
import com.example.studymanager.databinding.FragmentStudiesessieBinding
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.studiesessie.StudieSessieViewModel

class StudieSessieFragment : Fragment() {
    private lateinit var binding: FragmentStudiesessieBinding
    private val viewModel: StudieSessieViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        val database = getDatabase(activity.application)
        val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO)
        ViewModelProviders.of(
            this, StudieSessieViewModel.Factory(
                StudieSessieFragmentArgs.fromBundle(arguments!!).taskId, studieTaskRepository
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
            // buzz(longArrayOf(100, 100, 100, 100, 100, 100))

            //alert time is up !
            //  viewModel.onTimerFinished()  // dees gaf nen dikken error
        })
        return binding.root
    }

    // private fun buzz(pattern: LongArray) {
    //     val buzzer = activity?.getSystemService<Vibrator>()
    //     buzzer?.let {
    //         // Vibrate for 500 milliseconds
    //         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //             buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
    //         } else {
    //             //deprecated in API 26
    //             buzzer.vibrate(pattern, -1)
    //         }
    //     }
    // }


    override fun onStop() {
        super.onStop()
        binding.studiesessieViewModel?.persistTime()
    }
}