package com.example.studymanager.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymanager.domain.StudieTask

class HomeTaskAdapter : RecyclerView.Adapter<TaskItemViewHolder>() {
    var data = listOf<StudieTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        //holder.textview.item = item.eeeee.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(id, parent, false) as TaskItemView
        return TaskItemViewHolder(view)
    }
}