package com.example.studymanager.ui.home.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.studymanager.domain.StudieTask
import com.google.android.material.button.MaterialButton

@BindingAdapter("taskTitleString")
fun TextView.setTaskTitleString(item:StudieTask){
    item.let {
        text = item.studyTaskTitle
    }
}

@BindingAdapter("taskTimeRemFormatted")
fun MaterialButton.setTaskTitleString(item:StudieTask){
    item.let {
        // formatting moet hier nog aangepast worden wss
       text = item.remainingTaskTime.toInt().toString()
    }
}

fun MaterialButton.setVakAfkorting(item:StudieTask){
    item.let {
        text = item.vak.substring(0,2).toUpperCase()
    }

}