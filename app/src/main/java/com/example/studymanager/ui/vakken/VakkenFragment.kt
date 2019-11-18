package com.example.studymanager.vakken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStudiesessieBinding
import com.example.studymanager.databinding.FragmentVakkenBinding

class VakkenFragment : Fragment() {
    private lateinit var binding: FragmentVakkenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_vakken, container, false)
        //TODO implement
        return binding.root

    }

}