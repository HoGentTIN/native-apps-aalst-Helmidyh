package com.example.studymanager.ui.vakken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymanager.databinding.FragmentVakSessiesBinding
import com.example.studymanager.viewmodels.VakSessiesViewmodel
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskAdapter
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskListener

class VakSessieFragment : Fragment() {

    private lateinit var binding: FragmentVakSessiesBinding
    private lateinit var adapter: StudieTaskAdapter
    private val vakSessieViewModel: VakSessiesViewmodel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        ViewModelProviders.of(this, VakSessiesViewmodel.Factory(activity.application,VakSessieFragmentArgs.fromBundle(arguments!!).vakId))
            .get(VakSessiesViewmodel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        adapter = StudieTaskAdapter(StudieTaskListener { taskId ->
            this.findNavController().navigate(VakSessieFragmentDirections.actionVakSessieFragmentToStudieSessieFragment(taskId))

        })

        binding = FragmentVakSessiesBinding.inflate(inflater)
        binding.viewModel = vakSessieViewModel
        binding.vakSessiesRecyclerview.adapter = adapter
        binding.vakSessiesRecyclerview.layoutManager = (LinearLayoutManager(context))
        binding.lifecycleOwner = this

        startListeners()



        return binding.root
    }

    private fun startListeners() {
        binding.viewModel?.tasks?.observe(this, Observer { tasks ->
             adapter.submitList(tasks)
        })
    }
}