package com.example.studymanager.viewmodels.adapters.adapters

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.studymanager.R
import com.example.studymanager.domain.StudieTask
import com.google.android.material.button.MaterialButton
import java.util.concurrent.TimeUnit

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
        text = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(item.remainingTaskTime),
            TimeUnit.MILLISECONDS.toMinutes(item.remainingTaskTime) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(item.remainingTaskTime))
        );
    }
}

@BindingAdapter("taskVakFormatted")
fun MaterialButton.setVakAfkorting(item: StudieTask) {
    item.let {
        text = item.vak

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
            "Dat" -> setButtonColor(R.color.Databanken)
            else -> setButtonColor(R.color.colorPrimary)
        }

        if (item.vak.length > 2) {
            text = item.vak.substring(0, 3)
        } else {
            text = item.vak.substring(0, 2)
        }


    }
}