package com.example.studymanager.viewmodels.adapters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymanager.databinding.ListItemStudieVakBinding
import com.example.studymanager.domain.StudieVak

/**
 * @property VakkenAdapter = De adapter die we binden aan de vakken recyclerview
 */
class VakkenAdapter(var clickListener: StudieVakListener, var longClickListener: StudieVakLongClickListener) : ListAdapter<StudieVak, VakkenAdapter.ViewHolder>(StudieVakDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, longClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * Viewholder is de effectieve in memory presentatie van je item
     * @param binding = de binding met de ListItemStudieVak res file
     */
    class ViewHolder private constructor(val binding: ListItemStudieVakBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudieVak, clickListener: StudieVakListener, longClickListener: StudieVakLongClickListener) {
            binding.vak = item
            binding.clickListener = clickListener

            binding.materialCardView.setOnClickListener {
                clickListener.onClick(item)
            }

            binding.materialCardView.setOnLongClickListener {
                longClickListener.onLongClick(item)
                true
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemStudieVakBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class StudieVakDiffCallback : DiffUtil.ItemCallback<StudieVak>() {
    override fun areItemsTheSame(oldItem: StudieVak, newItem: StudieVak): Boolean {
        return oldItem.studieVakId == newItem.studieVakId
    }

    override fun areContentsTheSame(oldItem: StudieVak, newItem: StudieVak): Boolean {
        return oldItem == newItem
    }
}

class StudieVakListener(val clickListener: (vakkId: Int) -> Unit) {
    fun onClick(studieVak: StudieVak) = clickListener(studieVak.studieVakId)

}

class StudieVakLongClickListener(val longClickListener: (vakkId: Int) -> Unit) {
    fun onLongClick(studieVak: StudieVak) = longClickListener(studieVak.studieVakId)

}
