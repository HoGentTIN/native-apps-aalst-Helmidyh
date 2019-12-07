package com.example.studymanager.vakken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentStudiesessieBinding
import com.example.studymanager.databinding.FragmentVakkenBinding
import com.example.studymanager.viewmodels.adapters.adapters.*

class VakkenFragment : Fragment() {

    private lateinit var binding: FragmentVakkenBinding
    private lateinit var adapter: VakkenAdapter
    private val vakkenViewModel: VakkenViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        ViewModelProviders.of(this, VakkenViewModel.Factory(activity.application))
            .get(VakkenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        adapter = VakkenAdapter(StudieVakListener { vakkId ->
           // vakkenViewModel.onV
        })



        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_vakken, container, false)
        //TODO implement
        return binding.root

    }

}