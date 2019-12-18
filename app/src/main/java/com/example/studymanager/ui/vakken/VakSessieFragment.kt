package com.example.studymanager.ui.vakken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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
        ViewModelProviders.of(this, VakSessiesViewmodel.Factory(activity.application))
            .get(VakSessiesViewmodel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        adapter = StudieTaskAdapter(StudieTaskListener { taskId ->
            vakSessieViewModel.onStudieTaskClicked(taskId)
        })

    }
}