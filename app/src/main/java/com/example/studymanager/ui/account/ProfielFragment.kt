package com.example.studymanager.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.studymanager.App
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentProfileBinding
import com.example.studymanager.ui.activity.StartupActivity

class ProfielFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        binding.btnLogoutSend.setOnClickListener {
            App.getUserHelper().signOut()
            startActivity(Intent(context, StartupActivity::class.java))
        }

        return binding.root

    }


}