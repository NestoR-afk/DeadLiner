package com.example.deadliner.UI.deadlines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.deadliner.R
import com.example.deadliner.model.Deadline

import com.example.deadliner.viewmodel.DeadlineViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_deadline.*

/**
 * Deadlines fragment
 *
 * @constructor Create empty Deadlines fragment
 */
class DeadlinesFragment : Fragment() {
    /**
     * Deadline view model
     */
    private lateinit var deadlineViewModel: DeadlineViewModel

    /**
     * Adapter
     */
    private lateinit var adapter: DeadlineAdapter

    /**
     * On create view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        /**
         * Root
         */
        val root = inflater.inflate(R.layout.fragment_deadlines, container, false)

        deadlineViewModel = ViewModelProvider(this).get(DeadlineViewModel::class.java)
        adapter = DeadlineAdapter(inflater, deadlineViewModel)
        /**
         * List
         */
        var list = root.findViewById<RecyclerView>(R.id.deadlinesList)
        list.adapter = adapter

        deadlineViewModel.allDeadlines.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        return root
    }
}