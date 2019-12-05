package com.example.studymanager.studiesessie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStudiesessieCreatieBinding
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.ui.studiesessie.viewmodels.StudieSessieCrViewModel
import kotlinx.android.synthetic.main.fragment_studiesessie_creatie.*

class StudieSessieCrFragment : Fragment() {

    private lateinit var binding: FragmentStudiesessieCreatieBinding
    private val viewModel: StudieSessieCrViewModel by lazy {
        val activity: FragmentActivity = requireNotNull(this.activity) {
            "..."
        }
        ViewModelProviders.of(this, StudieSessieCrViewModel.Factory(activity.application))
            .get(StudieSessieCrViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_studiesessie_creatie,
            container,
            false
        )
        binding.viewModel = viewModel

        val VAKKEN = arrayOf("Android", "Databanken 3", "AI", "Analyse 3")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_item,
            VAKKEN
        )

        binding.filledExposedDropdown.setAdapter(adapter)

        if (binding.nmbrPickerHour != null) {
            binding.nmbrPickerHour.minValue = 0
            binding.nmbrPickerHour.maxValue = 8
            binding.nmbrPickerHour.wrapSelectorWheel = true
        }

        if (binding.nmbrPickerMinute != null) {
            binding.nmbrPickerMinute.minValue = 0
            binding.nmbrPickerMinute.maxValue = 59
            binding.nmbrPickerMinute.wrapSelectorWheel = true
        }

        var gekozenVak = -1
        binding.filledExposedDropdown.setOnItemClickListener { parent, view, position, id ->
            gekozenVak = id.toInt()
        }

        binding.btnTaskCreateSucces.setOnClickListener(
        ) {
            //1000 ms = 1 sec
            val longUur = binding.nmbrPickerHour.value.toLong() * 3600000
            val longMinuut = binding.nmbrPickerMinute.value.toLong() * 60000
            val totaal = longUur + longMinuut

            viewModel.createNewStudieTask(
                StudieTask(
                    0, binding.txtTaskCreateName.text.toString(),
                    totaal, totaal, filled_exposed_dropdown.adapter.getItem(gekozenVak).toString()
                )
            )
            findNavController().navigate(R.id.action_studieSessieCreatieFragment_to_homeFragment)
        }
        return binding.root
    }
}

