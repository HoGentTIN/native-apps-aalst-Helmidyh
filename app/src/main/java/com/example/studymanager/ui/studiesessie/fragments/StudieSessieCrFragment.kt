package com.example.studymanager.studiesessie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.studymanager.R
import com.example.studymanager.database.StudieDatabase
import com.example.studymanager.databinding.FragmentStudiesessieCreatieBinding
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.home.HomeViewModel
import com.example.studymanager.ui.home.HomeViewModelFactory
import com.example.studymanager.ui.home.adapters.HomeTaskAdapter
import com.example.studymanager.ui.studiesessie.viewmodels.StudieSessieCrViewModel
import com.example.studymanager.ui.studiesessie.viewmodels.StudieSessieCrViewModelFactory
import kotlinx.android.synthetic.main.fragment_studiesessie_creatie.*

class StudieSessieCrFragment : Fragment() {

    private lateinit var binding: FragmentStudiesessieCreatieBinding
    private lateinit var viewModel: StudieSessieCrViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = StudieDatabase.getInstance(application).studieDatabaseDAO
        val viewModelFactory = StudieSessieCrViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(StudieSessieCrViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_studiesessie_creatie,
            container,
            false
        )
        binding.viewModel = viewModel

        val VAKKEN = arrayOf("Android, Databanken 3, AI, Analyse 3")

        val adapter = ArrayAdapter(
            application.applicationContext,
            R.layout.dropdown_menu_popup_item,
            VAKKEN
        )

        val editTextFilledExposedDropdown = filled_exposed_dropdown
        editTextFilledExposedDropdown.setAdapter(adapter)

        if (binding.nmbrPickerHour != null) {
            binding.nmbrPickerHour.minValue = 0
            binding.nmbrPickerHour.maxValue = 20
            binding.nmbrPickerHour.wrapSelectorWheel = true
        }

        if (binding.nmbrPickerMinute != null) {
            binding.nmbrPickerMinute.minValue = 0
            binding.nmbrPickerMinute.maxValue = 20
            binding.nmbrPickerMinute.wrapSelectorWheel = true
        }

        binding.btnTaskCreateSucces.setOnClickListener(
        ) {
            val longUur = binding.nmbrPickerHour.value.toLong() * 10000000
            val longMinuut = binding.nmbrPickerMinute.value.toLong() * 1000000
            val totaal = longUur + longMinuut

            // iemand moet hier een nieuw studiesessie object aanmaken en persisteren
            // daarna gaan we terug naar de homepage waar de nieuwe studiesessietask is toegevoegd aan de recyclerview
            //    viewModel.createNewStudieTask(StudieTask(0,binding.txtTaskCreateName.text.toString(),totaal, totaal,))

            //   findNavController().navigate(StudieSessieCreatieFragmentDirections.actionStudieSessieCreatieFragmentToStudieSessieFragment())
        }

        return binding.root

    }


}

