package com.example.studymanager.ui.home.adapters

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.studymanager.R
import com.example.studymanager.domain.StudieTask
import com.google.android.material.button.MaterialButton
//databinding logica van adapter naar xml verplaats dmv deze classe
@BindingAdapter("taskTitleString")
fun TextView.setTaskTitleString(item: StudieTask) {
    item.let {
        text = item.studyTaskTitle
    }
}

@BindingAdapter("taskTimeRemFormatted")
fun MaterialButton.setTaskTitleString(item: StudieTask) {
    item.let {
        // formatting moet hier nog aangepast worden wss
        text = item.remainingTaskTime.toInt().toString()
    }
}

@BindingAdapter("taskVakFormatted")
fun MaterialButton.setVakAfkorting(item: StudieTask) {
    item.let {
        text = item.vak.substring(0, 2)

        fun setButtonColor(color: Int) {
            setBackgroundColor(
                ContextCompat.getColor(
                    context, color
                )
            )
        }

        when (text) {
            "Android" -> setButtonColor(R.color.Android)
            "AI" -> setButtonColor(R.color.AI)
            "Databanken" -> setButtonColor(R.color.Databanken)
            else -> setButtonColor(R.color.colorPrimary)
        }

    }
}