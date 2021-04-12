package com.example.deadliner.UI.deadlines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deadliner.R
import com.example.deadliner.model.Deadline
import com.example.deadliner.repository.DeadlineRepository
import com.example.deadliner.viewmodel.DeadlineViewModel
import kotlinx.android.synthetic.main.item_deadline.view.*
import java.util.*

class DeadlineAdapter(
        val inflater: LayoutInflater,
        private val deadlineViewModel: DeadlineViewModel
        ) : RecyclerView.Adapter<DeadlineAdapter.ViewHolder>() {

    private var deadlines = emptyList<Deadline>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_deadline, parent, false);
       return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return deadlines.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    private fun getItem(id: Int): Deadline {
        return deadlines[id]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activity: TextView = itemView.findViewById(R.id.deadline_activity)
        private val date: TextView = itemView.findViewById(R.id.deadline_date)
        private val desc: TextView = itemView.findViewById(R.id.deadline_description)

        fun bind(deadline: Deadline) {

            itemView.deleteButton.setOnClickListener{
            deadlineViewModel.deleteDeadline(deadline)
            }

            desc.text = deadline.description
            activity.text = deadline.subject
            date.text = android.text.format.DateFormat.format("EEE, d MMMM, yyyy", deadline.date)
        }
    }
    fun setData(deadlinesList: List<Deadline>) {
        this.deadlines = deadlinesList
        notifyDataSetChanged()
    }
}