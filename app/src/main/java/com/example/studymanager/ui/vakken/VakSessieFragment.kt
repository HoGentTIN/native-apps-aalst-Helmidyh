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
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studymanager.R
import com.example.studymanager.database.getDatabase
import com.example.studymanager.databinding.FragmentVakSessiesBinding
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.viewmodels.VakSessiesViewmodel
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskAdapter
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskListener
import com.example.studymanager.viewmodels.adapters.adapters.StudieTaskLongClickListener

/**
 * Analoog aan Homefragment maar adapter heeft andere data
 */
class VakSessieFragment : Fragment() {
    private lateinit var binding: FragmentVakSessiesBinding
    private lateinit var adapter: StudieTaskAdapter
    private val vakSessieViewModel: VakSessiesViewmodel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        val database = getDatabase(activity.application)
        val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO)
        ViewModelProviders.of(
            this, VakSessiesViewmodel.Factory(
                VakSessieFragmentArgs.fromBundle(arguments!!).vakId, studieTaskRepository
            )
        )
            .get(VakSessiesViewmodel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        adapter = StudieTaskAdapter(StudieTaskListener { taskId ->
            this.findNavController()
                .navigate(VakSessieFragmentDirections.actionVakSessieFragmentToStudieSessieFragment(taskId))

        }, StudieTaskLongClickListener { taskId ->

            MaterialDialog(binding.root.context).show {
                title(text = "Wenst u de gekozen task te verwijderen ?").titleFont
                positiveButton(R.string.add, "Remove") {
                    vakSessieViewModel.onStudieTaskLongClicked(taskId)
                }
                negativeButton(R.string.cancel, "Cancel")
            }
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