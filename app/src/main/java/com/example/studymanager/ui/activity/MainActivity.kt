package com.example.studymanager.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.studymanager.R
import com.example.studymanager.databinding.ActivityMainBinding
import com.pushbots.push.Pushbots


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
        Pushbots.sharedInstance().registerForRemoteNotifications()

    }
}
