package com.example.studymanager.vakken

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentVakkenBinding
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.viewmodels.adapters.adapters.StudieVakListener
import com.example.studymanager.viewmodels.adapters.adapters.StudieVakLongClickListener
import com.example.studymanager.viewmodels.adapters.adapters.VakkenAdapter
import kotlinx.android.synthetic.main.list_item_studie_vak.view.*

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

        binding = FragmentVakkenBinding.inflate(inflater)

        adapter = VakkenAdapter(StudieVakListener { vakId ->

            vakkenViewModel.onStudieVakClicked(vakId)

        }, StudieVakLongClickListener { vakkId ->

            MaterialDialog(binding.root.context).show {

                title(text = "Wenst u het gekozen vak te verwijderen ?").titleFont

                positiveButton(R.string.add, "Remove") {
                    vakkenViewModel.onStudieVakLongClicked(vakkId)
                }
                negativeButton(R.string.cancel, "Cancel")
            }
        })

        binding.recyclerviewVakken.adapter = adapter
        binding.vakkenViewModel = vakkenViewModel
        binding.recyclerviewVakken.setLayoutManager(LinearLayoutManager(context))
        binding.setLifecycleOwner(this)

        binding.btnVakToevoegen.setOnClickListener {
            MaterialDialog(layoutInflater.context).show {
                input(
                    hint = "Naam van vak",
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                )
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