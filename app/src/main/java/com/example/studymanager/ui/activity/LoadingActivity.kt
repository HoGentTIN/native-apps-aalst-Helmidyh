package com.example.studymanager.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.R
import com.example.studymanager.databinding.ActivityLoadingBinding
import com.example.studymanager.viewmodels.LoadingViewModel

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    private val viewModel: LoadingViewModel by lazy {
        ViewModelProviders
            .of(this, LoadingViewModel.Factory(application))
            .get(LoadingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)

        viewModel.isLoading.observe(this, Observer { loadingResult ->
            setResult(loadingResult)
            finish()
        })
    }
}