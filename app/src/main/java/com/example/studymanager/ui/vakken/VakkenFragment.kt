package com.example.studymanager.vakken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.studymanager.R
import com.example.studymanager.database.getDatabase
import com.example.studymanager.databinding.FragmentVakkenBinding
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import com.example.studymanager.viewmodels.adapters.adapters.StudieVakListener
import com.example.studymanager.viewmodels.adapters.adapters.StudieVakLongClickListener
import com.example.studymanager.viewmodels.adapters.adapters.VakkenAdapter

class VakkenFragment : Fragment() {
    private lateinit var binding: FragmentVakkenBinding
    private lateinit var adapter: VakkenAdapter
    private val vakkenViewModel: VakkenViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        val database = getDatabase(activity.application)
        val studieVakRepository = StudieVakRepository(database.studieVakDAO, database.statsDAO)
        val statsRepository = StatsRepository(database.statsDAO)
        ViewModelProviders.of(this, VakkenViewModel.Factory(studieVakRepository, statsRepository))
            .get(VakkenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * ClickListener en LongClickListener voor de StudieVak adapter
         * Click -> navigate
         * LongClick -> Delete + popup
         */
        adapter = VakkenAdapter(StudieVakListener { vakId ->

            this.findNavController()
                .navigate(VakkenFragmentDirections.actionVakkenFragmentToVakSessieFragment(vakId))

        },
            StudieVakLongClickListener { vakkId ->

                MaterialDialog(binding.root.context).show {
                    title(text = "Wenst u het gekozen vak te verwijderen ?").titleFont
                    positiveButton(R.string.add, "Remove") {
                        vakkenViewModel.onStudieVakLongClicked(vakkId)
                    }
                    negativeButton(R.string.cancel, "Cancel")
                }

            })
        binding = FragmentVakkenBinding.inflate(inflater)

        binding.recyclerviewVakken.adapter = adapter
        binding.vakkenViewModel = vakkenViewModel
        binding.recyclerviewVakken.layoutManager = (LinearLayoutManager(context))
        binding.lifecycleOwner = this

        /**
         * Vak toevoegen -> Popup + add
         */
        binding.btnVakToevoegen.setOnClickListener {
            MaterialDialog(layoutInflater.context).show {
                input(hint = "Naam van vak")
                positiveButton(R.string.add, "Add") {
                    vakkenViewModel.insert(StudieVak(0, getInputField().text.toString()))
                }
                negativeButton(R.string.cancel, "Cancel")
            }
        }

        startListeners()
        return binding.root
    }

    private fun startListeners() {
        binding.vakkenViewModel?.vakken?.observe(this, Observer { vakken ->
            adapter.submitList(vakken)
        })
    }

}