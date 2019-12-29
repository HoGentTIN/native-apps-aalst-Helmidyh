package com.example.studymanager.viewmodels.adapters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymanager.databinding.ListItemStudieTaskBinding
import com.example.studymanager.domain.StudieTask

class StudieTaskAdapter(var clickListener: StudieTaskListener, var longClickListener: StudieTaskLongClickListener) : ListAdapter<StudieTask, StudieTaskAdapter.ViewHolder>(StudieTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener,longClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemStudieTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudieTask, clickListener: StudieTaskListener, longClickListener: StudieTaskLongClickListener) {
            binding.studie = item
            binding.clickListener = clickListener

            binding.taskCardView.setOnClickListener {
                clickListener.onClick(item)
            }

            binding.taskCardView.setOnLongClickListener {
                longClickListener.onLongClick(item)
                true
            }

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
        return oldItem.studieTaskId == newItem.studieTaskId
    }

    override fun areContentsTheSame(oldItem: StudieTask, newItem: StudieTask): Boolean {
        return oldItem == newItem
    }
}

class StudieTaskListener(var clickListener: (taskId: Int) -> Unit) {
    fun onClick(studieTask: StudieTask) = clickListener(studieTask.studieTaskId)
}

class StudieTaskLongClickListener(val longClickListener: (taskId: Int) -> Unit) {
    fun onLongClick(studieTask: StudieTask) = longClickListener(studieTask.studieTaskId)

}