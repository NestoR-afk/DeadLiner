package com.example.deadliner.UI.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.deadliner.R
import com.example.deadliner.viewmodel.SubjectViewModel

/**
 * Schedule fragment
 *
 * @constructor Create empty Schedule fragment
 */
class ScheduleFragment : Fragment() {
    /**
     * Subject view model
     */
    private lateinit var subjectViewModel: SubjectViewModel

    /**
     * Adapter
     */
    private lateinit var adapter: ScheduleAdapter

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
        var root = inflater.inflate(R.layout.fragment_schedule, container, false)

        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        adapter = ScheduleAdapter(inflater, subjectViewModel, context)

        /**
         * List
         */
        var list = root.findViewById<RecyclerView>(R.id.subjectsList)
        list.adapter = adapter
        registerForContextMenu(list)

        subjectViewModel.allSubjects.observe(viewLifecycleOwner) {
            adapter.setData()
        }
        return root
    }
}