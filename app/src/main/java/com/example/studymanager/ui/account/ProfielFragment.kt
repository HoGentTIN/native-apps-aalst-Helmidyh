package com.example.studymanager.account

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.App
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentProfileBinding
import com.example.studymanager.databinding.FragmentStatsBinding
import com.example.studymanager.stats.StatsViewModel
import com.example.studymanager.ui.activity.StartupActivity

class ProfielFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfielViewmodel by lazy {
        val activity = requireNotNull(this.activity) {
            "..."
        }
        ViewModelProviders.of(
            this, ProfielViewmodel.Factory(
                activity.application
            )
        ).get(ProfielViewmodel::class.java)
    }
    override fun onCreateView( inflater: LayoutInflater,  container: ViewGroup?,   savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


      // binding.imgUserProfile.setImageDrawable(BitmapDrawable(context!!.resources,viewModel.imgBitmap))

        binding.btnLogoutSend.setOnClickListener {
            App.getUserHelper().signOut()
            startActivity(Intent(context, StartupActivity::class.java))
        }

        return binding.root

    }


}