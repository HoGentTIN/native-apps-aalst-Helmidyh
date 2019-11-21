package com.example.studymanager.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymanager.R
import com.example.studymanager.databinding.ListItemStudieTaskBinding
import com.example.studymanager.domain.StudieTask

class HomeTaskAdapter : ListAdapter<StudieTask, HomeTaskAdapter.ViewHolder>(StudieTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemStudieTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudieTask) {
            binding.studie = item
            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemStudieTaskBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }


    }

}

class StudieTaskDiffCallback : DiffUtil.ItemCallback<StudieTask>() {
    override fun areItemsTheSame(oldItem: StudieTask, newItem: StudieTask): Boolean {
        return oldItem.studyTaskId == newItem.studyTaskId
    }

    override fun areContentsTheSame(oldItem: StudieTask, newItem: StudieTask): Boolean {
        return oldItem == newItem
    }
}