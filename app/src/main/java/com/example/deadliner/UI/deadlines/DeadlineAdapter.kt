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

/**
 * Deadline adapter
 *
 * @property inflater
 * @property deadlineViewModel
 * @constructor Create empty Deadline adapter
 */
class DeadlineAdapter(
        val inflater: LayoutInflater,
        private val deadlineViewModel: DeadlineViewModel
) : RecyclerView.Adapter<DeadlineAdapter.ViewHolder>() {
    /**
     * Deadlines
     */
    private var deadlines = emptyList<Deadline>()

    /**
     * On create view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_deadline, parent, false)
        return ViewHolder(view)
    }

    /**
     * Get item count
     *
     * @return
     */
    override fun getItemCount(): Int {
        return deadlines.size
    }

    /**
     * On bind view holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Get item
     *
     * @param id
     * @return
     */
    private fun getItem(id: Int): Deadline {
        return deadlines[id]
    }

    /**
     * View holder
     *
     * @constructor
     *
     * @param itemView
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activity: TextView = itemView.findViewById(R.id.deadline_activity)
        private val date: TextView = itemView.findViewById(R.id.deadline_date)
        private val desc: TextView = itemView.findViewById(R.id.deadline_description)

        /**
         * Bind
         *
         * @param deadline
         */
        fun bind(deadline: Deadline) {

            itemView.deleteButton.setOnClickListener {
                deadlineViewModel.deleteDeadline(deadline)
            }

            desc.text = deadline.description
            activity.text = deadline.subject
            date.text = android.text.format.DateFormat.format("EEE, d MMMM, yyyy", deadline.date)
        }
    }

    /**
     * Set data
     *
     * @param deadlinesList
     */
    fun setData(deadlinesList: List<Deadline>) {
        this.deadlines = deadlinesList
        notifyDataSetChanged()
    }
}