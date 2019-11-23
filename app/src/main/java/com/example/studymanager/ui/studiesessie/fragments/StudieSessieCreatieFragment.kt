package com.example.studymanager.studiesessie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStudiesessieCreatieBinding

class StudieSessieCreatieFragment : Fragment() {

    private lateinit var binding: FragmentStudiesessieCreatieBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_studiesessie_creatie,
                container,
                false
            )

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
            findNavController().navigate(StudieSessieCreatieFragmentDirections.actionStudieSessieCreatieFragmentToStudieSessieFragment(totaal,binding.txtTaskCreateName.text.toString()))
        }


        return binding.root

    }


}

