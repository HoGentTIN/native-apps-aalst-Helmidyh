package com.example.studymanager.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymanager.R
import com.example.studymanager.R.id.*
import com.example.studymanager.domain.StudieTask
import com.google.android.material.button.MaterialButton

class HomeTaskAdapter : RecyclerView.Adapter<HomeTaskAdapter.ViewHolder>() {
    var data = listOf<StudieTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_studie_task, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(txt_list_item_task_title)
        val taskVakBtn: MaterialButton = itemView.findViewById(btn_vak_Afk)
        val taskRemTimeBtn: MaterialButton = itemView.findViewById(btn_task_time_rem)

        fun bind(item: StudieTask) {
            taskTitle.text = item.studyTaskTitle
            taskVakBtn.text = item.vak.substring(0, 2)
            taskRemTimeBtn.text = item.remainingTaskTime.toString()
            //TODO vak kleur linken met colors (case when 0->R.color.red else{random kleur})
        }


    }

}